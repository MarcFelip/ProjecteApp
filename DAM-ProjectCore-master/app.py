#!/usr/bin/python
# -*- coding: utf-8 -*-

import logging.config

import falcon

import messages
import middlewares
from resources import account_resources, common_resources, user_resources, course_resources
from settings import configure_logging

# LOGGING
mylogger = logging.getLogger(__name__)
configure_logging()


# DEFAULT 404
# noinspection PyUnusedLocal
def handle_404(req, resp):
    resp.media = messages.resource_not_found
    resp.status = falcon.HTTP_404


# FALCON
app = application = falcon.API(
    middleware=[
        middlewares.DBSessionManager(),
        middlewares.Falconi18n()
    ]
)
application.add_route("/", common_resources.ResourceHome())

application.add_route("/account/profile/showID", account_resources.ResourceAccountUserID())

application.add_route("/account/profile/show", account_resources.ResourceAccountUserProfile())
application.add_route("/account/profile/update", account_resources.ResourceAccountUpdateProfile())
application.add_route("/account/create_token", account_resources.ResourceCreateUserToken())
application.add_route("/account/delete_token", account_resources.ResourceDeleteUserToken())

application.add_route("/users/register", user_resources.ResourceRegisterUser())
application.add_route("/users/show/{username}", user_resources.ResourceGetUserProfile())

# List Courses
application.add_route("/courses/list", course_resources.ResourceGetCourses())
application.add_route("/courses/show/{course_id}", course_resources.ResourceGetCourse())
# Serveix per donar d'alta un nou curs -> usuari és l'owner
application.add_route("/courses/add", course_resources.ResourceAddCourse())
# Serveix per subscriureu's a un curs
application.add_route("/courses/{course_id}/add", course_resources.ResourceAddCourse())
# Serveix per desubscriureu's a un curs -> si es l'owner elimina el curs de la BD
application.add_route("/courses/{course_id}/{user_id}/delete", course_resources.ResourceDeleteCourse())
# Aquestes operacions son del owner (afegir horaris, examens i tasques)
application.add_route("/courses/{course_id}/schedules/add", course_resources.ResourceAddSchedules())
application.add_route("/courses/{course_id}/exams/add", course_resources.ResourceAddExams())
application.add_route("/courses/{course_id}/task/add", course_resources.ResourceAddTask())

# List Tasks
application.add_route("/courses/task/list", course_resources.ResourceGetTasks())
# Create the task and enroll in a course.
application.add_route("/courses/{course_id}/tasks/{course_task_id}/add", course_resources.ResourceAddStudentTask())

# Join to a created task (Groups).
application.add_route("/tasks/{course_id}/{task_id}/join", course_resources.ResourceAddStudentTask())
application.add_route("/tasks/{course_id}/{task_id}/update", course_resources.ResourceUpdateTask())
application.add_route("/tasks/{course_id}/{task_id}/delete", course_resources.ResourceDeleteTask())

# OJO: course_task_id -> representa la informació comuna -> es la tasca del curs
# OJO: task_id -> representa la copia de la tasca a la carpeta dels estudiants o faran el seu seguiment i podran fer grups.

#application.add_route("/users/insert_class", user_resources.ResourcePostSubject())
application.add_sink(handle_404, "")
