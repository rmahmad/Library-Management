require 'sinatra'
require 'data_mapper'
require 'json'

DataMapper::setup(:default, "postgres://root:password@localhost/database.db")

class Book
  include DataMapper::Resource
  property :id, Serial
  property :title, Text, :required => true
  property :publisher, Text, :required => true
  has n, :authorships
  has n, :authors, :through => :authorships
end

class Authorship
  include DataMapper::Resource
  belongs_to :author, :key => true
  belongs_to :book, :key => true
end

class Author
  include DataMapper::Resource
  property :id, Serial
  property :name, Text, :required => true
  has n, :authorships
  has n, :books, :through => :authorships
end

class Customer
  include DataMapper::Resource
  property :id, Serial
  property :firstname, Text
  property :lastname, Text
  property :registration, Date, :required => true
end

class Checkout
  include DataMapper::Resource
  property :id, Serial
  property :checkoutd, Date
  property :purchased, Date
  property :returnd, Date
  property :reservedd, Date
  property :book_id, Integer, :required => true
  property :cust_id, Integer, :required => true
end

DataMapper.finalize.auto_upgrade!

prefix = ""

Dir.foreach(File.join(File.dirname(__FILE__))) do |f|
  full_path = File.join(File.dirname(__FILE__), f)
  unless [".", "..", "index.html", "login.html"].include? f then
    route = prefix + "/" + f
    get route do
      send_file full_path
    end
  end
end

# Resource - books

# Format:   - json

# Create    - POST
# Show      - GET
# Update    - PUT
# Destroy   - DELETE

post '/books.json' do
  book = Book.new
  author = Author.new
  
  info = JSON.parse(params["book"])
  
  book.title = info["title"]
  book.publisher = info["publisher"]
  
  author.name = info["author"]
  
  author.books << book
  book.authors << author
  
  authorship = Authorship.new
  authorship.book = book
  authorship.author = author
  if authorship.save && book.save && author.save
    return book.to_json
  else
    [500, {"error" => "There was an error!"}.to_json]
  end
end

get '/books.json' do
  puts "Arr, Matey!"
  books = Book.all
  puts books.inspect
  books = books.to_json
end

get '/book/:id.json' do
  
end

put '/book/:id.json' do
  
end

delete '/book/:id.json' do
  
end

# Resource - customers

# Format:   - json

# Create    - POST
# Show      - GET
# Update    - PUT
# Destroy   - DELETE

post '/customers.json' do
  @customer = Customer.new
  @newcust = JSON.parse(params["customer"])
  @customer.title = @newcust["title"]
  @customer.author = @newcust["author"]
  @customer.publisher = @newcust["publisher"]
end

get '/customers.json' do
  @customers = Customer.all
  @customers = @customers.to_json
end

get '/customer/:id.json' do
  
end

put '/customer/:id.json' do
  
end

delete '/customer/:id.json' do
  
end

# Resource - checkouts

# Format:   - json

# Create    - POST
# Show      - GET
# Update    - PUT
# Destroy   - DELETE

post '/checkout.json' do
  @checkout = Checkout.new
  @newcheckout = JSON.parse(params["checkout"])
  @checkout.title = @newcheckout["title"]
  @checkout.author = @newcheckout["author"]
  @checkout.publisher = @newcheckout["publisher"]
end

get '/checkout.json' do
  @checkouts = Checkout.all
  @checkouts = @checkouts.to_json
end

get '/checkout/:id.json' do
  
end

put '/checkout/:id.json' do
  
end

delete '/checkout/:id.json' do
  
end




#post '/todos.json' do
#  @todo = Todo.new
#  @newtodo = JSON.parse(params["todo"])
#  @todo.content = @newtodo["content"]
#  @todo.name = @newtodo["name"]
#  @todo.done = @newtodo["done"]
#  
#  if @todo.save
#    return @todo.to_json
#  else
#    [500, {"error" => "There was an error!"}.to_json]
#  end
#end
#
#get '/todos.json' do
#  @todos = Todo.all
#  @todos = @todos.to_json
#end
#
#get '/todo/:id.json' do
#  
#end
#
#put '/todo/:id.json' do
#  
#end
#
#delete '/todo/:id.json' do
#  
#end


