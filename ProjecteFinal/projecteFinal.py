from app import create_app, db, cli
from app.models import User, Booking, Order, Product, Category, Products

app = create_app()
cli.register(app)


@app.shell_context_processor
def make_shell_context():
    return {'db': db, 'User': User, 'Booking': Booking, 'Order': Order, 'Products':Products, 'Product' : Product, 'Category': Category}
