from curses.ascii import isdigit
from datetime import datetime
from nis import cat
import uuid

from flask import render_template, flash, redirect, url_for, request, g, \
    jsonify, current_app, session
from flask_login import current_user, login_required
from flask_babel import _, get_locale
from langdetect import detect, LangDetectException
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

@bp.route('/create', methods=['GET','POST'])
@login_required
def create():
    categories = Category.query.all()
    form = BookingForm()
    if form.validate_on_submit():
        # retrieve or create order
        order = Order.query.filter_by(user_id=current_user.id, status='pending').first()
        if(not order):
            order = Order(user_id=current_user.id, status='pending')
        # parse dates
        startDate = datetime.strptime(range.split(' to ')[0], '%Y-%m-%d')
        endDate = datetime.strptime(range.split(' to ')[1], '%Y-%m-%d')

        str(uuid.uuid4())[:8]
        code=str(uuid.uuid4())[:8]

        booking = Booking(code=code, startDate=startDate, endDate=endDate)
        


        order.bookings.append(booking)
        
        db.session.add(order)
        db.session.commit()
        flash('Hem afegit la reserva al teu cistell, no oblidis pagar-la abans de tancar sessió')
        return render_template('index.html', categories=categories, session=session)
    else:
        flash('Seleccioni unes dates')
        return redirect(request.referrer)
        

