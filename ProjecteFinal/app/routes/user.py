from flask import Blueprint, render_template

from controllers.UserController import view, index
from models import User, Category

user_routes = Blueprint('user', __name__)

user_routes.route('/', methods=['GET'])(view)
user_routes.route('/db', methods=["GET", "POST"])(index)
