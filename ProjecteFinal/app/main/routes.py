from curses.ascii import isdigit
from datetime import datetime
from app.booking.forms import BookingForm
from flask import render_template, flash, redirect, url_for, request, g, \
    jsonify, current_app
from flask_login import current_user, login_required
from flask_babel import _, get_locale
from langdetect import detect, LangDetectException
from app import db
from app.main.forms import EditProfileForm, EmptyForm
from app.models import *
from app.translate import translate
from app.main import bp
from app.booking.forms import BookingForm


@bp.before_app_request
def before_request():
    if current_user.is_authenticated:
        current_user.last_seen = datetime.utcnow()
        db.session.commit()
    g.locale = str(get_locale())

@bp.route('/', methods=['GET', 'POST'])
@bp.route('/index', methods=['GET', 'POST'])
def index():
    categories = Category.query.all()
    return render_template('index.html', categories=categories)

@bp.route('/category/<cat>', methods=['GET', 'POST'])
def category(cat):
    if(cat.isnumeric()):
        category = Category.query.filter_by(id=cat).first()
    else:
        category = Category.query.filter_by(name=cat).first()   

    categories = Category.query.all()
    products = Product.query.filter_by(category_id=category.id).all()
    return render_template('category.html', categories=categories, category=category, products=products)

@bp.route('/category/<cat>/<id>')
def product(cat, id):
    form = BookingForm()
    categories = Category.query.all()
    product = Product.query.filter_by(id=id).first()
    return render_template('product.html', categories=categories, product=product, form=form)

@bp.route('/edit_profile', methods=['GET', 'POST'])
@login_required
def edit_profile():
    orders=current_user.orders


    form = EditProfileForm(current_user.username)
    if form.validate_on_submit():
        current_user.username = form.username.data
        current_user.about_me = form.about_me.data
        db.session.commit()
        flash(_('Your changes have been saved.'))
        return redirect(url_for('main.edit_profile'))
    elif request.method == 'GET':
        form.username.data = current_user.username
        form.about_me.data = current_user.about_me
    return render_template('edit_profile.html', title=_('Edit Profile'),
                           form=form, orders=orders)

@bp.route('/get-booked-dates/<id>', methods=['GET'])
def get_booked_dates(id):
    product = Product.query.filter_by(id=id).first()
    dates = product.get_booked_dates()
    return jsonify(dates)


@bp.route('/translate', methods=['POST'])
@login_required
def translate_text():
    return jsonify({'text': translate(request.form['text'],
                                      request.form['source_language'],
                                      request.form['dest_language'])})
