from flask import Blueprint, render_template

from controllers.UserController import index
from models.User import User



user_bp = Blueprint('user_bp', __name__)

user_bp.route('/list', methods=['GET'])(index)