from datetime import datetime, date, timedelta
from hashlib import md5
from time import time
from flask import current_app
from flask_login import UserMixin
from werkzeug.security import generate_password_hash, check_password_hash
import jwt
from app import db, login


class User(UserMixin, db.Model):
    id = db.Column(db.Integer, primary_key=True)
    username = db.Column(db.String(64), index=True, unique=True)
    email = db.Column(db.String(120), index=True, unique=True)
    password_hash = db.Column(db.String(128))
    about_me = db.Column(db.String(140))
    last_seen = db.Column(db.DateTime, default=datetime.utcnow)

    def __repr__(self):
        return '<User {}>'.format(self.username)

    def set_password(self, password):
        self.password_hash = generate_password_hash(password)

    def check_password(self, password):
        return check_password_hash(self.password_hash, password)

    def avatar(self, size):
        digest = md5(self.email.lower().encode('utf-8')).hexdigest()
        return 'https://www.gravatar.com/avatar/{}?d=identicon&s={}'.format(
            digest, size)


    def get_reset_password_token(self, expires_in=600):
        return jwt.encode(
            {'reset_password': self.id, 'exp': time() + expires_in},
            current_app.config['SECRET_KEY'], algorithm='HS256')

    @staticmethod
    def verify_reset_password_token(token):
        try:
            id = jwt.decode(token, current_app.config['SECRET_KEY'],
                            algorithms=['HS256'])['reset_password']
        except:
            return
        return User.query.get(id)


# Helper table

class Product(db.Model):
    __tablename__ = 'product'
    id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String)
    price_per_day = db.Column(db.Float)
    photo_url = db.Column(db.String)
    # A product belongs to a category
    category_id = db.Column(db.Integer, db.ForeignKey('category.id'),
        nullable=True)

    # Get the bookings that contain this product
    def get_bookings(self):
        results =  Products.query.filter_by(product_id =self.id).all()
        bookings = []
        for result in results:
            bookings.append(result.booking_id)
        return bookings

    # Get the dates in which the product is booked
    def get_booked_dates(self):
        results =  Products.query.filter_by(product_id =self.id).all()
        dates = []
        for result in results:
            booking = Booking.query.filter_by(id=result.booking_id).first()

            # Copied from https://stackoverflow.com/questions/7274267/print-all-day-dates-between-two-dates
            start_date = booking.startDate
            end_date = booking.endDate
            delta = end_date - start_date   # returns timedelta

            for i in range(delta.days + 2):
                day = start_date + timedelta(days=i)
                dates.append(day)

        return dates

class Products(db.Model):
    __tablename__ = 'products'
    product_id = db.Column(db.Integer, db.ForeignKey('product.id'), primary_key=True)
    booking_id = db.Column(db.Integer, db.ForeignKey('booking.id'), primary_key=True)
    
class Booking (db.Model):
    __tablename__ = 'booking'
    id = db.Column(db.Integer, primary_key=True)
    code = db.Column(db.String)
    startDate = db.Column(db.DateTime, default=datetime.utcnow)
    endDate = db.Column(db.DateTime, default=datetime.utcnow)

    # A booking should be inside an order
    order_id = db.Column(db.Integer, db.ForeignKey('order.id'))

    # A booking can have various products on the same dates
    products = db.relationship('Product', secondary=Products.__tablename__, lazy='subquery',
        backref=db.backref('bookings', lazy=True))

    def __repr__(self):
        return '<Booking code: %s, startDate: %s>' % (self.code, self.startDate.strftime("%m/%d/%Y"))
    
    def get_booked_dates(self):
        dates = []
        # Copied from https://stackoverflow.com/questions/7274267/print-all-day-dates-between-two-dates
        start_date = self.startDate
        end_date = self.endDate
        delta = end_date - start_date   # returns timedelta
        for i in range(delta.days + 2):
            day = start_date + timedelta(days=i)
            dates.append(day)
        return dates

class Order(db.Model):
    __tablename__ = 'order'
    id = db.Column(db.Integer, primary_key=True)
    user_id = db.Column(db.Integer, db.ForeignKey('user.id'))
    created_at = db.Column(db.DateTime())
    status = db.Column(db.String)

    # A order can have many bookings
    bookings = db.relationship('Booking', backref='order',
                                lazy='dynamic')
    

class Category(db.Model):
    __tablename__ = 'category'
    id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String)
    # A category can have many products
    db.relationship('Product', backref='category', lazy=True)



@login.user_loader
def load_user(id):
    return User.query.get(int(id))
