# MohamedSalah
News Application using Java TCP Socket Programming with simple GUI.
This project aims to deliver News from the Client publisher to Multiple Subscribed Clients using MQTT protocol, where the broker is responsible to deliver the data between both.
The Publisher choose the category and write the text, then send it to the broker which redirect it to all the connected clients.
The broker is responsible for sending the new article to the clients who signed up for it ONLY. For example if client chose "Sports" Category,then the broker is responsible to send him only the Sports category and block the rest.
