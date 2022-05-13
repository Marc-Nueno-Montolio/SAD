from datetime import datetime
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
    categories = Category.query.all()
    category = Category.query.filter_by(name=cat).first()
    products = Product.query.filter_by(category_id=category.id).all()
    return render_template('category.html', categories=categories, category=category, products=products)

@bp.route('/category/<cat>/<id>')
def product(cat, id):
    categories = Category.query.all()
    product = Product.query.filter_by(id=id).first()
    return render_template('product.html', categories=categories, product=product)

@bp.route('/edit_profile', methods=['GET', 'POST'])
@login_required
def edit_profile():
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
                           form=form)

@bp.route('/get-booked-dates/<id>', methods=['POST'])
def get_booked_dates(id):
    return jsonify({'data':'ok'})


@bp.route('/translate', methods=['POST'])
@login_required
def translate_text():
    return jsonify({'text': translate(request.form['text'],
                                      request.form['source_language'],
                                      request.form['dest_language'])})
