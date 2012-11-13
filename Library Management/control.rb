require 'sinatra'
require 'data_mapper'
require 'json'

enable :sessions
DataMapper::setup(:default, "sqlite3://#{Dir.pwd}/test.db")

class Book
	include DataMapper::Resource
	property :id, Serial
	property :title, Text, :required => true
	property :publisher, Text, :required => true
	has n, :authorships, :constraint => :destroy
	has n, :authors, :through => :authorships
	has n, :checkouts, :constraint => :destroy
	has 1, :customer, :through => :checkouts
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
	has n, :authorships, :constraint => :destroy
	has n, :books, :through => :authorships
end

class Customer 
	include DataMapper::Resource
	property :id, Serial
	property :firstname, Text
	property :lastname, Text
	property :registration, Date, :required => true
	property :username, Text, :unique => true
	property :password, Text
	property :admin, Boolean
	has n, :checkouts, :constraint => :destroy
	has n, :books, :through => :checkouts
end

class Checkout
	include DataMapper::Resource
	property :id, Serial
	property :checkoutdate, Date
	property :purchasedate, Date
	property :returndate, Date
	property :reserveddate, Date
	property :returned, Boolean
	property :current, Boolean
	belongs_to :book, :key => true
	belongs_to :customer, :key => true
end

DataMapper.finalize.auto_upgrade!
# Set Default Session Variable Values
#session['login'] = false
#session['admin'] = false
#session['username'] = nil

# Resource - books

# Format:   - json

# Create    - POST
# Show      - GET
# Update    - PUT
# Destroy   - DELETE

prefix = "/views"
Dir.foreach(File.join(File.dirname(__FILE__), "views")) do |f|
	full_path = File.join(File.dirname(__FILE__), "views", f)
	unless [".", "..", "admin.html", "customer.html", "login.html", "index.html"].include? f then
		route = prefix + "/" + f
		get route do
			send_file full_path
		end
	end
end

get prefix + '/login.html' do
	if session['login'] == true
		redirect prefix + '/index.html'
	else
		template = Tilt.new(File.join(File.dirname(__FILE__), "public", "login.erb"))
		template.render(self)
	end
end

post prefix + '/login.html' do
	# This is kind of a hack, but we don't care? Create the default administrator account if it doesn't exist.
	adminuser = Customer.first(:username => 'admin')

	if(!adminuser)
		adminuseruser = Customer.new
		adminuseruser.username = 'admin'
		adminuseruser.password = 'tiger'
		adminuseruser.admin = 1
		time = Time.now
		adminuseruser.registration = "#{time.year}-#{time.month}-#{time.day}"
		adminuseruser.firstname = "Default"
		adminuseruser.lastname = "Administrator"
		adminuseruser.save
	end

	@username = params['username-field']
	@password = params['password-field']
	user = Customer.first(:username => @username)
	if(user && @password == user['password'])
		# Login Successful
		session['login'] = true
		session['admin'] = user['admin']
		session['name'] = user['username']
		session['user_id'] = user['id']
		session['error'] = nil
		redirect prefix + '/index.html'
	else
		session['error'] = true
		redirect prefix + '/login.html'
	end
end

get prefix + '/register.html' do
	template = Tilt.new(File.join(File.dirname(__FILE__), "public", "register.erb"))
	template.render(self)
end

post prefix + '/register.html' do
	@username = params['username-field']
	@password = params['password-field']
	@rpass = params['repeat-field']
	@firstname = params['firstname-field']
	@lastname = params['lastname-field']

	user = Customer.first(:username => @username)
	if @password != @rpass
		session['registration_error'] = 'Passwords Don\'t Match'
		redirect prefix + '/register.html'
	elsif @username.empty? || @password.empty? || @rpass.empty? || @firstname.empty? || @lastname.empty?
		session['registration_error'] = 'No Fields Can Be Blank'
		redirect prefix + '/register.html'
	elsif user
		session['registration_error'] = 'User Name Already Exists'
		redirect prefix + '/register.html'
	else
		user = Customer.new
		user.username = @username
		user.password = @password
		user.admin = 0
		user.firstname = @firstname
		user.lastname = @lastname
		time = Time.now
		user.registration = "#{time.year}-#{time.month}-#{time.day}"

		if !user.save
			puts 'Errors Follow'
			puts 'Errors Follow'
			puts 'Errors Follow'
			puts 'Errors Follow'
			puts 'Errors Follow'
			puts 'Errors Follow'
			users.errors.each do |error|
				puts error
			end
			session['registration_error'] = 'Internal Error: Could Not Save Information. Try Again Later.'
			redirect prefix + '/register.html'
		else
			user = Customer.first(:username => @username)
			session['registration_error'] = nil
			session['login'] = true
			session['admin'] = false
			session['name'] = @username
			session['error'] = nil
			session['user_id'] = user['id']
			redirect prefix + '/index.html'
		end
	end
end

get prefix + '/logout.html' do
	session['login'] = nil
	session['admin'] = nil
	session['name'] = nil
	session['error'] = nil
	session['user_id'] = nil
	redirect prefix + '/login.html'
end

get prefix + '/admin.html' do
	puts session
	if !session['login']
		redirect prefix + '/login.html'
	elsif !session['admin']
		redirect prefix + '/customer.html'
	else
		file = File.join(File.dirname(__FILE__), "views", "admin.html")
		file = File.open(file)
		return file.read
	end
end

get prefix + '/customer.html' do
	if !session['login']
		redirect prefix + '/login.html'
	elsif session['admin']
		redirect prefix + '/admin.html'
	else
		file = File.join(File.dirname(__FILE__), "views", "customer.html")
		file = File.open(file)
		return file.read
	end
end

get prefix + '/index.html' do
	if !session['login']
		redirect prefix + '/login.html'
	elsif session['admin']
		redirect prefix + '/admin.html'
	else
		redirect prefix + '/customer.html'
	end
end

get '/' do
	redirect prefix + '/index.html'
end

imageprefix = "/views/images"
Dir.foreach(File.join(File.dirname(__FILE__), "views", "images")) do |f|
	full_path = File.join(File.dirname(__FILE__), "views", "images", f)
	unless [".", ".."].include? f then
		route = imageprefix + "/" + f
		get route do
			send_file full_path
		end
	end
end

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
	books = Book.all(:order => [:title.asc])
	data = []
	for book in books do
		author = book.authors
		for element in author
			if book.customer
				for checkout in book.checkouts do
					if(checkout.current)
						data << Hash["id", book.id, "title", book.title, "publisher", book.publisher, "author", element.name, "checkout", true]
					else
						data << Hash["id", book.id, "title", book.title, "publisher", book.publisher, "author", element.name, "checkout", false]
					end
				end
			else
				data << Hash["id", book.id, "title", book.title, "publisher", book.publisher, "author", element.name, "checkout", false]
			end
		end
	end
	#  books = books.to_json
	data = data.to_json
end

get '/book/:id.json' do

end

put '/book/:id.json' do
	book = Book.get(params["id"])
	author = book.authors[0]
	book.title = params["title"]
	author.name = params["author"]
	book.publisher = params["publisher"]
	if book.save && author.save
		return book.to_json
	else
		[500, {"error" => "There was an error!"}]
	end
end

delete '/book/:id.json' do
	book = Book.get(params["id"])
	if book
		book.destroy
	else
		[500, {"error" => "There was an error!"}]
	end
end

# Resource - customers

# Format:   - json

# Create    - POST
# Show      - GET
# Update    - PUT
# Destroy   - DELETE

post '/customers.json' do
	customer = Customer.new
	newcust = JSON.parse(params["customer"])
	customer.firstname = newcust["firstname"]
	customer.lastname = newcust["lastname"]
	time = Time.now
	customer.registration = "#{time.year}-#{time.month}-#{time.day}"
	if customer.save
		return customer.to_json
	else
		[500, {"error" => "There was an error!"}.to_json]
	end

end

get '/customers.json' do
	customers = Customer.all(:order => [:lastname.asc])
	for customer in customers do
		puts customer.inspect
	end
	customers = customers.to_json
end

get '/customer/profile.json' do
	customer = Customer.get(session["user_id"])
	fullname = customer.firstname + " " + customer.lastname
	data = Hash["name", fullname]
	checkouts = customer.checkouts
	checkouts_data = []
	currentdate = Date.new
	for checkout in checkouts  do
		overdue = checkout.returndate < Date.today
		puts checkout.inspect
		if(checkout.current)
			checkouts_data << Hash["Book", checkout.book.title, "ID", checkout.id, "Author", checkout.book.authors, "Publisher", checkout.book.publisher, "Return date", checkout.returndate, "overdue", overdue]
		end
	end

	data["checkouts"] = checkouts_data
	puts "data: #{data.inspect}"
	data.to_json
end

put '/customer/:id.json' do
	customer = Customer.get(params["id"])
	customer.lastname = params["newcontent"]
	if customer.save
		return customer.to_json
	else
		[500, {"error" => "There was an error!"}]
	end
end

delete '/customer/:id.json' do
	customer = Customer.get(params["id"])
	if customer
		customer.destroy
	else
		[500, {"error" => "There was an error!"}]
	end
end

# Resource - checkouts

# Format:   - json

# Create    - POST
# Show      - GET
# Update    - PUT
# Destroy   - DELETE

post '/checkout.json' do
	checkout = Checkout.new
	puts "params #{params.inspect}"
	newcheckout = JSON.parse(params["checkout"])
	puts "newcheckout #{newcheckout.inspect}"
	book = Book.get(newcheckout["book_id"])
	checkedout = false
	if(book)
		for checkout in book.checkouts do
			if checkout.current
				checkedout = true
			end
		end
	end
	if !checkedout
		cust = Customer.get(session["user_id"])
		checkout.book = book
		checkout.customer = cust
		checkout.current = true
		checkout.returned = false
		time = Time.now
		checkout.checkoutdate = "#{time.year}-#{time.month}-#{time.day}"
		time = time + (60*60*24*14)
		checkout.returndate = "#{time.year}-#{time.month}-#{time.day}"
		book.customer = cust
		cust.books << book
		if checkout.save
			return checkout.to_json
		else
			[500, {"error" => "There was an error!"}]
		end
	else
		puts "i'm here!"
		[500, "Book is already checked out."]
	end
end

get '/checkout.json' do
	@checkouts = Checkout.all
	@checkouts = @checkouts.to_json
end

get '/checkout/book/:id.json' do
	book = Book.get(params["id"])
	cust = book.customer
	data = []
	checkout = Checkout.all(:book => book) & Checkout.all(:customer => cust) & Checkout.all(:current => true)
	puts checkout.inspect
	puts cust.inspect
	puts book.inspect
	if book && cust && checkout && checkout.length > 0
		data << Hash["book_title", book.title, "cust_name", "#{cust.firstname} #{cust.lastname}", "publisher", book.publisher, "author", book.authors, "return_date", checkout[0].returndate]
		puts data.inspect
	else
		[500, {"error" => "There was an error!"}]
	end
	data = data.to_json
end

post '/checkout/return/:id.json' do
	id = params["id"].to_i
	checkout = Checkout.all(:id => id)[0]
	checkout.returned = true
	checkout.current = false
	puts "checkout!1 #{checkout.inspect}"
	if(checkout.save)
		puts "yay!"
	else
		[500, {"error" => "There was an error!"}]
	end
end

put '/checkout/:id.json' do

end

delete '/checkout/:id.json' do

end

post '/booksearch.json' do
	puts "search.json"
	search = JSON.parse(params["search"])
	id = search["book_id"].to_i
	title = search["title"]
	publisher = search["publisher"]
	author_name = search["author"]
	query = []
	data = []
	if(id && id != 0)
		book = Book.get(id)
		if((title != "" && title == book.title) || (publisher != "" && publisher == book.publisher) || (author_name != nil && book.authors.include?(author_name)) || (title == "" && publisher == "" && author_name == ""))
			query << book
		end
	else
		if(title != "")
			if(publisher != "")
				if(author_name != "")
					query_authors = Author.all(:name => author_name)
					if(!query_authors.empty?)
						query = Book.all(:title => title) & Book.all(:publisher => publisher)
						for book in query do
							authors = book.authors
							for author in query_authors do
								if(!authors.include?(author))
									query.delete(book)
								end
							end
						end
					end
				else
					query = Book.all(:title => title) & Book.all(:publisher => publisher)
				end
			elsif(author_name != "")
				query_authors = Author.all(:name => author_name)
				if(!query_authors.empty?)
					books = Book.all(:title => title)
					for book in books do
						authors = book.authors
						for author in query_authors do
							if(authors.include?(author))
								query << book
							end
						end
					end
				end
				puts "query: #{query.inspect}"
			else
				query = Book.all(:title => title)
			end
		elsif(publisher != "")
			if(author_name != "")
				query_authors = Author.all(:name => author_name)
				if(!query_authors.empty?)
					query_books = Book.all(:publisher => publisher, :order => [:title.asc])
					for book in query_books do
						authors = book.authors
						for author in query_authors do
							if(authors.include?(author))
								query << book
							end
						end
					end
				end
			else
				query = Book.all(:publisher => publisher, :order => [:title.asc])
			end
		elsif(author_name != "")
			query_authors = Author.all(:name => author_name)
			if(!query_authors.empty?)
				books = Book.all(:order => [:title.asc])
				for book in books
					authors = book.authors
					for author in query_authors do
						if(authors.include?(author))
							query << book
						end
					end
				end
			end
		end
	end

	for entry in query do
		data << Hash["title", entry.title, "publisher", entry.publisher, "author", entry.authors, "id", entry.id]
	end
	data = data.to_json
end

post '/custsearch.json' do
	puts "search.json"
	search = JSON.parse(params["search"])
	id = search["cust_id"].to_i
	firstname = search["firstname"]
	lastname = search["lastname"]
	query = []
	data = []
	if(id && id != 0)
		cust = Customer.get(id)
		if((firstname != "" && firstname == cust.firstname) || (lastname != nil && lastname == cust.lastname) || (firstname == "" && lastname == ""))
			query << cust
		end
	else
		if(firstname != "")
			if(lastname != "")
				query = Customer.all(:firstname => firstname) & Customer.all(:lastname => lastname)
			else
				query = Customer.all(:firstname => firstname)
			end
		elsif(lastname != "")
			query = Customer.all(:lastname => lastname)
		end
	end

	for entry in query do
		data << Hash["ID", entry.id, "firstname", entry.firstname, "lastname", entry.lastname, "registration", entry.registration, "id", entry.id]
	end
	data = data.to_json
end


