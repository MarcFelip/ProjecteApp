#!/bin/bash

pip install --upgrade pip
pip install -r /app/requirements.txt

export PYTHONPATH=$PYTHONPATH:/app

#Comentar la linea dabaix perque els registres a la base de dades es guardin i no es resetegin
python /app/dev/reset_database.py

gunicorn -b [::]:8000 app:app --reload
