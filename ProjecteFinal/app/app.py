import os
from flask import Flask, render_template
from flask_migrate import Migrate

from models.User import db

from routes.user_bp import user_bp

template_dir = os.path.abspath('views')
static_dir = os.path.abspath('static')

app = Flask(__name__, template_folder=template_dir,  static_folder=static_dir)

app.config.from_object('config')

db.init_app(app)
migrate = Migrate(app, db)
app.register_blueprint(user_bp)


if __name__ == '__main__':
    app.debug = True
    app.run()