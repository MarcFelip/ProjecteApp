#!/usr/bin/python
# -*- coding: utf-8 -*-

import logging

import falcon
from falcon.media.validators import jsonschema
from sqlalchemy.exc import IntegrityError
from sqlalchemy.orm.exc import NoResultFound
import re

import messages
from db.models import User, GenereEnum
from hooks import requires_auth
from resources.base_resources import DAMCoreResource
from resources.schemas import SchemaRegisterUser, SchemaPostClass

mylogger = logging.getLogger(__name__)


@falcon.before(requires_auth)
class ResourceGetUserProfile(DAMCoreResource):
    def on_get(self, req, resp, *args, **kwargs):
        super(ResourceGetUserProfile, self).on_get(req, resp, *args, **kwargs)

        if "username" in kwargs:
            try:
                aux_user = self.db_session.query(User).filter(User.username == kwargs["username"]).one()

                resp.media = aux_user.public_profile
                resp.status = falcon.HTTP_200
            except NoResultFound:
                raise falcon.HTTPBadRequest(description=messages.user_not_found)


class ResourceRegisterUser(DAMCoreResource):
    @jsonschema.validate(SchemaRegisterUser)
    def on_post(self, req, resp, *args, **kwargs):
        super(ResourceRegisterUser, self).on_post(req, resp, *args, **kwargs)

        aux_user = User()

        try:

            aux_user.username = req.media["username"]
            aux_user.password = req.media["password"]

            email_validator = '^\w+([\.-]?\w+)*@gmail.com'

            aux_user.email = req.media["email"]
            print(re.search(email_validator, aux_user.email))

            if re.search(email_validator, aux_user.email) is None:
                raise falcon.HTTPBadRequest(messages.invalid_mail)

            #r1 = re.findall(r"^\w+", req.media["email"])
            #print("Usuario" + r1)
            #aux_user.username = r1

            self.db_session.add(aux_user)

            try:
                self.db_session.commit()
            except IntegrityError:
                raise falcon.HTTPBadRequest(description=messages.user_exists)

        except KeyError:
            raise falcon.HTTPBadRequest(description=messages.parameters_invalid)

        resp.status = falcon.HTTP_200


# @falcon.before(requires_auth)
# class ResourcePostSubject(DAMCoreResource):
#     @jsonschema.validate(SchemaPostClass)
#     def on_post(self, req, resp, *args, **kwargs):
#         super(ResourcePostSubject, self).on_post(req, resp, *args, **kwargs)
#
#         aux_subjects = Subject()
#
#         try:
#
#             current_user = req.context["auth_user"]
#             print(current_user.name)
#
#             aux_subjects.classe = req.media["classe"]
#             aux_subjects.description = req.media["description"]
#
#             print(re.search("Prova Classe ", aux_subjects.classe))
#
#             query = self.db_session.query(Subject).filter(Subject.classe == aux_subjects.classe)
#             print(query)
#             result = query.all()
#             print(result)
#             if not result:
#                 self.db_session.add(aux_subjects)
#
#             try:
#                 self.db_session.commit()
#             except IntegrityError:
#                 raise falcon.HTTPBadRequest(description=messages.user_exists)
#
#         except KeyError:
#             raise falcon.HTTPBadRequest(description=messages.parameters_invalid)
#
#         resp.status = falcon.HTTP_200


#@falcon.before(requires_auth)
#class ResourceGetSubject(DAMCoreResource):
    # def on_get(self, req, resp, *args, **kwargs):
        #  super(ResourceGetSubject, self).on_get(req, resp, *args, **kwargs)

        # try:
           # aux_subject = self.db_session.query(Subject).filter(User.username == kwargs["username"]).one()

            #resp.media = aux_subject.public_Subjects
            #resp.status = falcon.HTTP_200
           # except NoResultFound:
# raise falcon.HTTPBadRequest(description=messages.user_not_found)
