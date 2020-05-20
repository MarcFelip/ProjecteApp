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

# Create and delete Courses
application.add_route("/courses/add", course_resources.ResourceAddCourse())
application.add_route("/courses/{course_id}/add", course_resources.ResourceAddCourse())
application.add_route("/courses/{course_id}/{user_id}/delete", course_resources.ResourceDeleteCourse())

# List Tasks
application.add_route("/courses/task/list", course_resources.ResourceGetTasks())

# Create the task and enroll in a course.
application.add_route("/courses/{course_id}/task/add", course_resources.ResourceAddTask())
# Join to a created task (Groups).
application.add_route("/courses/{course_id}/task/{task_id}/join", course_resources.ResourceAddTask())
application.add_route("/courses/{course_id}/task/{task_id}/update", course_resources.ResourceUpdateTask())
application.add_route("/courses/{course_id}/task/{task_id}/delete", course_resources.ResourceDeleteTask())








#application.add_route("/users/insert_class", user_resources.ResourcePostSubject())
application.add_sink(handle_404, "")
