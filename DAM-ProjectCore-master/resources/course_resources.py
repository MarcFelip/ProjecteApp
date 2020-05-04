#!/usr/bin/python
# -*- coding: utf-8 -*-
import datetime
import logging
import random
import string

import falcon
from falcon.media.validators import jsonschema
from pymysql import Date
from sqlalchemy.exc import IntegrityError
from sqlalchemy.orm.exc import NoResultFound
import re

import messages
from db.models import Course, Enrollment, Task, Assigment
from hooks import requires_auth
from resources.base_resources import DAMCoreResource
from settings import DATE_DEFAULT_FORMAT

mylogger = logging.getLogger(__name__)


@falcon.before(requires_auth)
class ResourceGetCourses(DAMCoreResource):
    def on_get(self, req, resp, *args, **kwargs):
        super(ResourceGetCourses, self).on_get(req, resp, *args, **kwargs)

        current_user = req.context["auth_user"]
        response_student_courses = list()
        aux_student_courses= self.db_session.query(Enrollment.course_id, Course.name)\
            .join(Course).filter(Enrollment.user_id == current_user.id )

        if aux_student_courses is not None:
            for student_current_course in aux_student_courses.all():
                response_item = {
                    'id': student_current_course[0],
                    'name': student_current_course[1]
                }
                response_student_courses.append(response_item)

        resp.media = response_student_courses
        resp.status = falcon.HTTP_200


@falcon.before(requires_auth)
class ResourceAddTask(DAMCoreResource):
    def on_post(self, req, resp, *args, **kwargs):
        super(ResourceAddTask, self).on_post(req, resp, *args, **kwargs)

        current_user = req.context["auth_user"]
        aux_task = Task()

        if not "task_id" in kwargs:
            try:
                # Create the task
                aux_task.deadline = datetime.datetime.strptime(req.media["deadline"], DATE_DEFAULT_FORMAT).date()
                aux_task.tittle = req.media["tittle"]
                aux_task.details = req.media["details"]
                aux_task.total_points = req.media["total_points"]
                aux_task.join_secret = ''.join(random.choices(string.ascii_uppercase + string.digits, k=20))
                self.db_session.add(aux_task)
                self.db_session.commit()
                task_id = aux_task.id
            except KeyError:
                raise falcon.HTTPBadRequest(description=messages.parameters_invalid)
        else:
            task_id = kwargs["task_id"]
            try:
                join_secret = req.media["join_secret"]
                task = self.db_session.query(Task).filter(task_id == Task.id, join_secret == Task.join_secret).all()
                if not task:
                    raise falcon.HTTPBadRequest(description="join_secret incorrecte!")
            except KeyError:
                raise falcon.HTTPBadRequest(description=messages.parameters_invalid)


        if "course_id" in kwargs:
            try:
                # Associate the task with the user and the course
                aux_assigment = Assigment()
                aux_assigment.user_id = current_user.id
                aux_assigment.task_id = task_id
                aux_assigment.course_id = kwargs["course_id"]
                self.db_session.add(aux_assigment)
                self.db_session.commit()

                resp.status = falcon.HTTP_200
            except KeyError:
                raise falcon.HTTPBadRequest(description=messages.parameters_invalid)
            except IntegrityError:
                raise falcon.HTTPBadRequest(description="Aquesta tasca ja ha estat assignada per aquest usuari en aquest curs.")
        else:
            raise falcon.HTTPMissingParam("course_id")

@falcon.before(requires_auth)
class ResourceUpdateTask(DAMCoreResource):
    def on_put(self, req, resp, *args, **kwargs):
        super(ResourceUpdateTask, self).on_put(req, resp, *args, **kwargs)

        current_user = req.context["auth_user"]

        if "course_id" in kwargs and "task_id" in kwargs:
            course_id = kwargs["course_id"]
            task_id = kwargs["task_id"]
            assigment = self.db_session.query(Assigment)\
                .filter(Assigment.user_id == current_user.id,
                        Assigment.task_id == task_id,
                        Assigment.course_id == course_id).all()
            if not assigment:
                    raise falcon.HTTPBadRequest(description="not allowed to update this task, first you need to join.")
            else:
                try:
                    task = self.db_session.query(Task).filter(Task.id == task_id).first()
                    task.deadline = datetime.datetime.strptime(req.media["deadline"], DATE_DEFAULT_FORMAT).date()
                    task.tittle = req.media["tittle"]
                    task.details = req.media["details"]
                    task.total_points = req.media["total_points"]

                    self.db_session.commit()
                    resp.status = falcon.HTTP_200
                except KeyError:
                    raise falcon.HTTPBadRequest(description=messages.parameters_invalid)


@falcon.before(requires_auth)
class ResourceDeleteTask(DAMCoreResource):
    def on_delete(self, req, resp, *args, **kwargs):
        super(ResourceDeleteTask, self).on_delete(req, resp, *args, **kwargs)

        current_user = req.context["auth_user"]

        if "course_id" in kwargs and "task_id" in kwargs:
            course_id = kwargs["course_id"]
            task_id = kwargs["task_id"]
            assigment = self.db_session.query(Assigment) \
                .filter(Assigment.user_id == current_user.id,
                        Assigment.task_id == task_id,
                        Assigment.course_id == course_id).all()
            if not assigment:
                raise falcon.HTTPBadRequest(description="not allowed to update this task, first you need to join.")
            else:
                try:
                    self.db_session.query(Task).filter(Task.id == task_id).delete()
                    self.db_session.commit()
                    resp.status = falcon.HTTP_200
                except KeyError:
                    raise falcon.HTTPBadRequest(description=messages.parameters_invalid)