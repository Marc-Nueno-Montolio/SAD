import sys
from flask import render_template, redirect, url_for, request, abort
from models import User, Booking
from flask_sqlalchemy import SQLAlchemy

db = SQLAlchemy()


def view():
    return render_template('index.html')

def index():
    booking = Booking(code='ABC' )

    user = User.query.filter_by(name='Marc').first()


    
    users = [user]
    
    
    return render_template('results.html', results=users)


