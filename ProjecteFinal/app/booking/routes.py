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

        product = Product.query.filter_by(id=int(form.productId.data)).first()    
        
        # parse dates
        startDate = datetime.strptime(form.date.data.split(' to ')[0], '%Y-%m-%d')
        endDate = datetime.strptime(form.date.data.split(' to ')[1], '%Y-%m-%d')

        str(uuid.uuid4())[:8]
        code=str(uuid.uuid4())[:8]

        booking = Booking(code=code, startDate=startDate, endDate=endDate)
        booking.products.append(product)


        order.bookings.append(booking)
        
        db.session.add(order)
        db.session.commit()
        flash('Hem afegit la reserva al teu cistell')
        return redirect(url_for('main.index'))
    else:
        flash('Seleccioni unes dates')
        return redirect(request.referrer)
        


@bp.route('/delete/<id>', methods=['GET','POST'])
@login_required
def delete(id):

        booking = Booking.query.filter_by(id=id).first()
        if(booking):
            db.session.delete(booking)
            db.session.commit()
            flash('Hem esborrat la reserva')
        return redirect(url_for('main.index'))
        
