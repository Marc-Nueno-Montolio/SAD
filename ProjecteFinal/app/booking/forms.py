from flask import request
from flask_wtf import FlaskForm
from wtforms import StringField, SubmitField, TextAreaField, HiddenField
from wtforms.validators import ValidationError, DataRequired, Length
from flask_babel import _, lazy_gettext as _l
from app.models import User

class EmptyForm(FlaskForm):
    submit = SubmitField('Submit')


class BookingForm(FlaskForm):
    submit = SubmitField()
    date = StringField(validators=[DataRequired()])
    productId = HiddenField(validators=[DataRequired()])