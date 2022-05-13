from curses.ascii import isdigit
from datetime import datetime
from nis import cat
import uuid

from flask import render_template, flash, redirect, url_for, request, g, \
    jsonify, current_app, session
from flask_login import current_user, login_required
from flask_babel import _, get_locale

from app import db
from app.main.forms import EditProfileForm, EmptyForm
from app.models import *
from app.translate import translate
from app.booking import bp
from app.booking.forms import BookingForm


@bp.before_app_request
def before_request():
    if current_user.is_authenticated:
        current_user.last_seen = datetime.utcnow()
        db.session.commit()
    g.locale = str(get_locale())

# Todo: routes related with orders here...