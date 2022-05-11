#https://stackoverflow.com/questions/29872867/using-flask-migrate-with-flask-script-and-application-factory
from app import create_app
from flask_migrate import MigrateCommand, Manager

manager = Manager(create_app)
manager.add_option("-c", "--config", dest="config_module", required=False)
manager.add_command('db', MigrateCommand)