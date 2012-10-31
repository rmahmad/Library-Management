require 'sinatra'
require 'mongo'
require 'json'

connection = Mongo::Connection.new

db = connection.db("rubydb", :safe => true)

$todo = db["Todo"]
$counters = db["Counters"]

def idCounter()
  $counters.find_and_modify({:query => {"collection" => "Todo"}, :update => {'$inc' => {"c" => 1}}})
end

post '/todos.json' do
  newtodo = JSON.parse(params["todo"])
  $todo.insert({"_id" => idCounter()["c"],"name" => newtodo["name"], "content" => newtodo["content"], "done" => newtodo["done"]})
  return 200
end

get '/todos/:id.json' do
  
end

put '/todos/:id.json' do
  newcontent = params["newcontent"]
  $todo.update({"_id" => params["id"].to_i}, {"$set" => {"content" => newcontent}})
end

delete '/todos/:id.json' do
  $todo.remove("_id" => params["id"].to_i)
end

put '/todos/:id/completed.json' do
  $todo.update({"_id" => params["id"].to_i}, {"$set" => {"done" => true}})
end

get '/todos.json' do
  todos = $todo.find().to_a
  todos = todos.to_json
end