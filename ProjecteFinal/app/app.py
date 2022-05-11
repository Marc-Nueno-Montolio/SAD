import os
from flask import Flask
from flask_migrate import Migrate

from models import db
from routes.user import user_routes

template_dir = os.path.abspath('views')
static_dir = os.path.abspath('static')
migrate = Migrate()


def create_app(test_config=None):
    # create and configure the app
    app = Flask(__name__, template_folder=template_dir,  static_folder=static_dir)

    app.config.from_pyfile("config.py")

    db.init_app(app)
    migrate.init_app(app, db)

    with app.app_context():
        db.create_all()
    
    # Register app routes here
    app.register_blueprint(user_routes)
    

    return app