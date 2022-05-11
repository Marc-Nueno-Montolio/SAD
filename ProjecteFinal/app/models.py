from flask_sqlalchemy import SQLAlchemy
db = SQLAlchemy()


class User(db.Model):
    __tablename__ = 'user'
    id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String)
    role = db.Column(db.String)
    level = db.Column(db.String)
    email = db.Column(db.String(120))
    password = db.Column(db.String(120))
    # One person can have many bookings
    bookings = db.relationship('Booking', backref='user', lazy=True)

class Booking (db.Model):
    __tablename__ = 'booking'
    id = db.Column(db.Integer, primary_key=True)
    code = db.Column(db.String)
    startDate = db.Column(db.DateTime())
    endDate = db.Column(db.DateTime())
    user_id =  db.Column(db.Integer, db.ForeignKey('user.id'), nullable=False)
    order = db.relationship('Order', backref='booking', lazy=True)
    
class Order(db.Model):
    __tablename__ = 'order'
    id = db.Column(db.Integer, primary_key=True)
    user_id = db.Column(db.Integer, db.ForeignKey('user.id'))
    created_at = db.Column(db.DateTime())
    status = db.Column(db.String)
    # A booking has a order
    booking_id = db.Column(db.Integer, db.ForeignKey('booking.id'),
        nullable=False)
    # A order has many products
    

class Product(db.Model):
    __tablename__ = 'product'
    id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String)
    price_per_day = db.Column(db.Float)
    photo_url = db.Column(db.String)
    # A product belongs to a category
    category_id = db.Column(db.Integer, db.ForeignKey('category.id'),
        nullable=True)
    # A product can be in many orders 


class Category(db.Model):
    __tablename__ = 'category'
    id = db.Column(db.Integer, primary_key=True)
    # A category can have many products
    db.relationship('Product', backref='category', lazy=True)


