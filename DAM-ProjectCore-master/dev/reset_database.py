#!/usr/bin/python
# -*- coding: utf-8 -*-

import datetime
import logging
import os

from sqlalchemy.sql import text

import db
import settings
from db.models import SQLAlchemyBase, User, GenereEnum, UserToken, Course, Schedule, Task, Exam, Enrollment, Assigment
from settings import DEFAULT_LANGUAGE

# LOGGING
mylogger = logging.getLogger(__name__)
settings.configure_logging()


def execute_sql_file(sql_file):
    sql_folder_path = os.path.join(os.path.dirname(__file__), "sql")
    sql_file_path = open(os.path.join(sql_folder_path, sql_file), encoding="utf-8")
    sql_command = text(sql_file_path.read())
    db_session.execute(sql_command)
    db_session.commit()
    sql_file_path.close()


if __name__ == "__main__":
    settings.configure_logging()

    db_session = db.create_db_session()

    # -------------------- REMOVE AND CREATE TABLES --------------------
    mylogger.info("Removing database...")
    SQLAlchemyBase.metadata.drop_all(db.DB_ENGINE)
    mylogger.info("Creating database...")
    SQLAlchemyBase.metadata.create_all(db.DB_ENGINE)



    # -------------------- CREATE USERS --------------------
    mylogger.info("Creating default users...")
    # noinspection PyArgumentList
    user_admin = User(
        created_at=datetime.datetime(2020, 1, 1, 0, 1, 1),
        username="admin",
        email="admin@damcore.com",
        name="Administrator",
        surname="DamCore",
        genere=GenereEnum.male
    )
    user_admin.set_password("DAMCoure")

    # noinspection PyArgumentList
    user_1= User(
        created_at=datetime.datetime(2020, 1, 1, 0, 1, 1),
        username="usuari1",
        email="usuari1@gmail.com",
        name="usuari",
        surname="1",
        birthdate=datetime.datetime(1989, 1, 1),
        genere=GenereEnum.male
    )
    user_1.set_password("a1s2d3f4")
    user_1.tokens.append(UserToken(token="656e50e154865a5dc469b80437ed2f963b8f58c8857b66c9bf"))

    # noinspection PyArgumentList
    user_2 = User(
        created_at=datetime.datetime(2020, 1, 1, 0, 1, 1),
        username="user2",
        email="user2@gmail.com",
        name="user",
        surname="2",
        birthdate=datetime.datetime(2017, 1, 1),
        genere=GenereEnum.male
    )
    user_2.set_password("r45tgt")
    user_2.tokens.append(UserToken(token="0a821f8ce58965eadc5ef884cf6f7ad99e0e7f58f429f584b2"))

    db_session.add(user_admin)
    db_session.add(user_1)
    db_session.add(user_2)
    db_session.commit()

 # -------------------- CREATE COURSES --------------------
    mylogger.info("Creating default course...")
    course_dam = Course(
        name = "Desenvolupament d'Apliacions per a dispositius mòvils",
        description="Aquesta es la descripció de DAM...",
    )

    course_intic = Course(
        name="Innovació a les TIC",
        description="Aquesta es la descripció de INTIC...",
    )

    db_session.add(course_dam)
    db_session.add(course_intic)
    db_session.commit()

    # -------------------- CREATE ENROLLMENTS --------------------
    mylogger.info("Creating default enrollments...")
    user1DAM = Enrollment(
        course_id=1,
        user_id=2,
    )

    user1NTIC = Enrollment(
        course_id=2,
        user_id=2,
    )

    user2NTIC = Enrollment(
        course_id=2,
        user_id=3,
    )

    db_session.add(user1DAM)
    db_session.add(user1NTIC)
    db_session.add(user2NTIC)
    db_session.commit()

# -------------------- CREATE TASKS --------------------
    mylogger.info("Creating default tasks...")
    t1 = Task(
        tittle="Task 1 DAM",
        details = "Details Task 1 DAM",
        deadline = datetime.datetime(2020, 6, 6),
        total_points = 10,
        join_secret="1234"
    )

    t2 = Task(
        tittle="Task 1 INTIC",
        details="Details Task 1 INTIC",
        deadline=datetime.datetime(2020, 5, 21),
        total_points=10,
        join_secret="1234"
    )

    t3 = Task(
        tittle="Task 1 INTIC",
        details="Details Task 1 INTIC",
        deadline=datetime.datetime(2020, 5, 21),
        total_points=10,
        join_secret="1234"
    )

    db_session.add(t1)
    db_session.add(t2)
    db_session.add(t3)
    db_session.commit()

    user1task1INTIC = Assigment(
        task_id=1,
        user_id=2,
        course_id=1
    )

    user1task2INTIC = Assigment(
        task_id=2,
        user_id=2,
        course_id=1
    )

    db_session.add(user1task1INTIC)
    db_session.add(user1task2INTIC)
    db_session.commit()


    # -------------------- CREATE EXAMNS --------------------
    mylogger.info("Creating default exams...")
    user1exam1DAM = Exam(
        course_id=1,
        user_id=2,
        tittle="Exam 1 DAM",
        details="Details Exam 1 DAM",
        deadline=datetime.datetime(2017, 1, 1),
        total_points=10,
        mark=8
    )

    db_session.add(user1exam1DAM)
    db_session.commit()

 # -------------------- CREATE SCHEDULES --------------------
    mylogger.info("Creating default schedules...")
    schedule1INTIC = Schedule(
        day="Monday",
        start="15:00",
        end="17:00",
        place="Videoconferencia",
        course_id=2
    )

    schedule2INTIC = Schedule(
        day="Tuesday",
        start="15:00",
        end="17:00",
        place="Videoconferencia",
        course_id=2
    )

    schedule1DAM = Schedule(
        day="Tuesday",
        start="17:00",
        end="19:00",
        place="Videoconferencia",
        course_id=1
    )


    db_session.add(schedule1INTIC)
    db_session.add(schedule2INTIC)
    db_session.add(schedule1DAM)
    db_session.commit()
    db_session.close()