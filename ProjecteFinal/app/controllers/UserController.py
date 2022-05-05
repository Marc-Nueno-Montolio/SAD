import sys
from flask import render_template, redirect, url_for, request, abort
from models.User import User
from flask_sqlalchemy import SQLAlchemy
db = SQLAlchemy()

def index():
    users = User.query.all()
    return render_template('index.html', users = users)


