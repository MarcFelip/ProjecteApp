#!/usr/bin/python
# -*- coding: utf-8 -*-

import binascii
import datetime
import enum
import logging
import os
from builtins import getattr
from urllib.parse import urljoin

import falcon
from passlib.hash import pbkdf2_sha256
from sqlalchemy import Column, Date, DateTime, Enum, ForeignKey, Integer, Unicode, \
    UnicodeText, Table
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.ext.hybrid import hybrid_method, hybrid_property
from sqlalchemy.orm import relationship
from sqlalchemy_i18n import make_translatable

import messages
from db.json_model import JSONModel
import settings

mylogger = logging.getLogger(__name__)

SQLAlchemyBase = declarative_base()
make_translatable(options={"locales": settings.get_accepted_languages()})


def _generate_media_url(class_instance, class_attibute_name, default_image=False):
    class_base_url = urljoin(urljoin(urljoin("http://{}".format(settings.STATIC_HOSTNAME), settings.STATIC_URL),
                                     settings.MEDIA_PREFIX),
                             class_instance.__tablename__ + "/")
    class_attribute = getattr(class_instance, class_attibute_name)
    if class_attribute is not None:
        return urljoin(urljoin(urljoin(urljoin(class_base_url, class_attribute), str(class_instance.id) + "/"),
                               class_attibute_name + "/"), class_attribute)
    else:
        if default_image:
            return urljoin(urljoin(class_base_url, class_attibute_name + "/"), settings.DEFAULT_IMAGE_NAME)
        else:
            return class_attribute


class GenereEnum(enum.Enum):
    male = "M"
    female = "F"

class TaskStatusEnum(enum.Enum):
    completed = "Completed"
    ongoing = "Ongoing"
    closed = "Closed"



class UserToken(SQLAlchemyBase):
    __tablename__ = "users_tokens"

    id = Column(Integer, primary_key=True)
    token = Column(Unicode(50), nullable=False, unique=True)
    user_id = Column(Integer, ForeignKey("users.id", onupdate="CASCADE", ondelete="CASCADE"), nullable=False)
    user = relationship("User", back_populates="tokens")

# StudentsCourseAssication (Enrollment)

class Enrollment(SQLAlchemyBase, JSONModel):
    __tablename__ = "enrollments"

    course_id = Column(Integer, ForeignKey('courses.id',onupdate="CASCADE", ondelete="CASCADE"), primary_key=True)
    user_id = Column(Integer, ForeignKey('users.id',onupdate="CASCADE", ondelete="CASCADE"), primary_key=True)
    mark = Column(Integer)

    student_enrolled = relationship("Course", back_populates="students_enrolled")
    course_enrolled = relationship("User", back_populates="courses_enrolled")

    @hybrid_property
    def json_model(self):
        return {
            "course_id": self.course_id,
            "user_id": self.user_id,
        }


# class Task(SQLAlchemyBase, JSONModel):
#     __tablename__ = "tasks"
#
#     id = Column(Integer, primary_key=True)
#     tittle = Column(Unicode(50))
#     details = Column(Unicode(200))
#     deadline = Column(Date)
#     total_points = Column(Integer)
#     join_secret = Column(UnicodeText, nullable=False)
#
#     task_assigments = relationship("Assigment", back_populates="task_assigment", cascade="all, delete-orphan")
#
#     @hybrid_property
#     def json_model(self):
#         return {
#             "id":self.id,
#             "tittle": self.tittle,
#             "details": self.details,
#             "deadline": self.deadline.strftime(
#                 settings.DATE_DEFAULT_FORMAT) if self.deadline is not None else self.deadline,
#             "total_points":self.total_points
#         }

class Milestone(SQLAlchemyBase, JSONModel):
    __tablename__ = "milestones"

    id = Column(Integer, primary_key=True)
    tittle = Column(Unicode(50))
    details = Column(Unicode(200))
    deadline = Column(Date)
    completed = Column(Integer)
    task_id = Column(Integer, ForeignKey('tasks.id', onupdate="CASCADE", ondelete="CASCADE"), primary_key=True)
    task = relationship("Task", back_populates="milestones")

    @hybrid_property
    def json_model(self):
        return {
            "id":self.id,
            "tittle": self.tittle,
            "deadline": self.deadline.strftime(
                settings.DATE_DEFAULT_FORMAT) if self.deadline is not None else self.deadline,
            "details": self.details,
            "completed": self.completed,
        }

class Task(SQLAlchemyBase, JSONModel):
    __tablename__ = "tasks"

    id = Column(Integer, primary_key=True)
    course_task_id = Column(Integer, ForeignKey('course_tasks.id', onupdate="CASCADE", ondelete="CASCADE"))
    join_secret = Column(UnicodeText, nullable=False)


    task_assigments = relationship("Assigment", back_populates="task_assigment", cascade="all, delete-orphan")
    milestones = relationship("Milestone", back_populates="task", cascade="all, delete-orphan")

    @hybrid_property
    def json_model(self):
        return {
            "id":self.id,
            "join_secret": self.join_secret,
            "milestones": [milestone.json_model for milestone in self.milestones],
        }


class Assigment(SQLAlchemyBase, JSONModel):
    __tablename__ = "assigments"

    task_id = Column(Integer, ForeignKey('tasks.id',onupdate="CASCADE", ondelete="CASCADE"), primary_key=True)
    course_id = Column(Integer, ForeignKey('courses.id',onupdate="CASCADE", ondelete="CASCADE"), primary_key=True)
    user_id = Column(Integer, ForeignKey('users.id',onupdate="CASCADE", ondelete="CASCADE"), primary_key=True)
    mark = Column(Integer)

    task_assigment = relationship("Task", back_populates="task_assigments")
    student_assigment = relationship("Course", back_populates="students_assigments")
    course_assigment = relationship("User", back_populates="courses_assigments")

    @hybrid_property
    def json_model(self):
        return {
            "course_id": self.course_id,
            "user_id": self.user_id,
        }



class Exam(SQLAlchemyBase, JSONModel):
    __tablename__ = "exams"

    id = Column(Integer, autoincrement=True, primary_key=True)
    course_id = Column(Integer, ForeignKey('courses.id',onupdate="CASCADE", ondelete="CASCADE"), primary_key=True)
    tittle = Column(Unicode(50))
    details = Column(Unicode(200))
    date = Column(Date)
    total_points = Column(Integer)
    percent = Column(Integer)
    course = relationship("Course", back_populates="exams")

    @hybrid_property
    def json_model(self):
        return {
            "id": self.id,
            "total_points": self.total_points,
            "date": self.date.strftime(
                settings.DATE_DEFAULT_FORMAT) if self.date is not None else self.date,
            "tittle": self.tittle,
            "details": self.details,
            "percent": self.percent
        }


class CourseTask(SQLAlchemyBase, JSONModel):
    __tablename__ = "course_tasks"

    id = Column(Integer, autoincrement=True, primary_key=True)
    course_id = Column(Integer, ForeignKey('courses.id',onupdate="CASCADE", ondelete="CASCADE"), primary_key=True)
    tittle = Column(Unicode(50))
    details = Column(Unicode(200))
    deadline = Column(Date)
    total_points = Column(Integer)
    percent = Column(Integer)

    @hybrid_property
    def json_model(self):
        return {
            "id": self.id,
            "total_points": self.total_points,
            "deadline": self.deadline.strftime(
                settings.DATE_DEFAULT_FORMAT) if self.deadline is not None else self.deadline,
            "tittle": self.tittle,
            "details": self.details,
            "percent": self.percent
        }

    course = relationship("Course", back_populates="tasks")



class Schedule(SQLAlchemyBase, JSONModel):
    __tablename__ = "schedules"

    id = Column(Integer, primary_key=True)
    day = Column(Unicode(50))
    start = Column(Unicode(50))
    end = Column(Unicode(50))
    place = Column(Unicode(50))
    course_id = Column(Integer,ForeignKey("courses.id",onupdate="CASCADE", ondelete="CASCADE"), nullable=False)
    course = relationship("Course", back_populates="schedules")

    @hybrid_property
    def json_model(self):
        return {
            "day": self.day,
            "start": self.start,
            "end": self.end,
            "place": self.place,
        }

class User(SQLAlchemyBase, JSONModel):
    __tablename__ = "users"

    id = Column(Integer, primary_key=True)
    created_at = Column(DateTime, default=datetime.datetime.now, nullable=False)
    username = Column(Unicode(50))
    password = Column(UnicodeText, nullable=False)
    email = Column(Unicode(255), nullable=False, unique=True)
    tokens = relationship("UserToken", back_populates="user", cascade="all, delete-orphan")
    name = Column(Unicode(50))
    surname = Column(Unicode(50))
    birthdate = Column(Date)
    genere = Column(Enum(GenereEnum))
    phone = Column(Unicode(50))
    photo = Column(Unicode(255))

    courses_enrolled = relationship("Enrollment", back_populates="course_enrolled", cascade="all, delete-orphan")
    courses_assigments = relationship("Assigment", back_populates="course_assigment",cascade="all, delete-orphan")
    #courses_exams = relationship("Exam", back_populates="course_exam", cascade="all, delete-orphan")
    courses_created = relationship("Course", back_populates="owner", cascade="all, delete-orphan")
    #subjects_enrolled = relationship("Subject", back_populates="registered")

    @hybrid_property
    def public_profile(self):
        return {
            "created_at": self.created_at.strftime(settings.DATETIME_DEFAULT_FORMAT),
            "username": self.username,
            "genere": self.genere.value,
            "photo": self.photo,
        }

    @hybrid_method
    def set_password(self, password_string):
        self.password = pbkdf2_sha256.hash(password_string)

    @hybrid_method
    def check_password(self, password_string):
        return pbkdf2_sha256.verify(password_string, self.password)

    @hybrid_method
    def create_token(self):
        if len(self.tokens) < settings.MAX_USER_TOKENS:
            token_string = binascii.hexlify(os.urandom(25)).decode("utf-8")
            aux_token = UserToken(token=token_string, user=self)
            return aux_token
        else:
            raise falcon.HTTPBadRequest(title=messages.quota_exceded, description=messages.maximum_tokens_exceded)

    @hybrid_property
    def json_model(self):
        return {
            "created_at": self.created_at.strftime(settings.DATETIME_DEFAULT_FORMAT),
            "username": self.username,
            "email": self.email,
            "name": self.name,
            "surname": self.surname,
            "birthdate": self.birthdate.strftime(
                settings.DATE_DEFAULT_FORMAT) if self.birthdate is not None else self.birthdate,

            # TODO: Comprovar que el valor del genere es null o no, return una cosa o altre
            "genere": self.genere.value,
            "phone": self.phone,
            "photo": self.photo,
        }


# Course
class Course(SQLAlchemyBase, JSONModel):
    __tablename__ = "courses"

    id = Column(Integer, primary_key=True)
    name = Column(Unicode(100), nullable=False, unique=True)
    description = Column(Unicode(200))

    owner_id = Column(Integer,ForeignKey("users.id",onupdate="CASCADE", ondelete="CASCADE"), nullable=False)
    owner = relationship("User", back_populates="courses_created")

    students_enrolled = relationship("Enrollment", back_populates="student_enrolled", cascade="all, delete-orphan")
    students_assigments = relationship("Assigment", back_populates="student_assigment", cascade="all, delete-orphan")
    #students_exams = relationship("Exam", back_populates="student_exam", cascade="all, delete-orphan")
    schedules = relationship("Schedule", back_populates="course", cascade="all, delete-orphan")
    exams = relationship("Exam", back_populates="course", cascade="all, delete-orphan")
    tasks = relationship("CourseTask", back_populates="course", cascade="all, delete-orphan")

    @hybrid_property
    def json_model(self):
        return {
            "id": self.id,
            "name": self.name,
            "description": self.description,
            "schedules": [schedule.json_model for schedule in self.schedules],
            "exams":[exam.json_model for exam in self.exams],
            "tasks": [task.json_model for task in self.tasks],
        }




