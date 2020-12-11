# FANDOME

## Table of Contents
1. [Overview](#Overview)
2. [Product Spec](#Product-Spec)
3. [Wireframes](#Wireframes)
4. [Schema](#Schema)
5. [Build Progress](#Build-Progress)

## Overview
### Description
Fandome is a social media app that celebrates the engament of fans in a shared community. You can connect with people who have similar interests as yours in group based 'fandom hubs' where you can find a collection of relevent blogs, posts, and group chats.

### App Evaluation
- **Category:** Chat/Social
- **Mobile:** Mobile first experience
- **Story:** Allows to share posts with each other about their favorite fandoms
- **Market:** Anyone who is interested in fan based content: cosplayers, content creators etc. You will have the ability to make posts as well as enter the 'fandom hub' of any of your favorite fandoms.
- **Habit:** Users can engage with one another through post interaction and chat 
- **Scope:** Basic social media app

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**
- [x]  Users can create a new account
- [x]  Users can log in
- [x]  Users can create a new text post
- [x]  Users can post to their profile or to a specifc Fandom Hub
- [x]  Users can view recent posts of fandoms they follow in their feed
- [x]  Users can view their own recent posts in their feed
- [x]  Users can search for Fandom hubs
- [x]  Users can enter a Fandom hub page after search
- [x] Users can view a Fandom hub page

**Optional Nice-to-have Stories**
* Users can join a fandom hub
* Users can direct message each other
* Users can create a new post with images and/or text
* Users can comment on each others posts
* Users can like a post and/or a comment
* Users are directed to other user profile when clicking on their username on posts
* Users can unfollow a fandom
* Users view a splash screen on initial start up
* Users can benefit from a more complex tag based system by adding personalized tags
* Users can view blog/post reccomendations based on user's current interests
* Users can join more fandom hubs from the original two in the beta
* Users can bookmark favorite posts
* Users can view settings tab
* Users can edit their profiles like the biography and profile picture
* Users can create group chat (live chat). User also has the ability to turn off notifications
* Users can follow other users
* Users can block/report other users
* Users can view favorite fandoms (in the nav bar)
* Users can view a more complex Hub gallery screen (includes related hubs, trending blogs/posts)
* Users can view trending hubs

### 2. Screen Archetypes

* Login screen
    * Users can log in
* Register Screen
    * Users can create a new account
* Feed
    * Users can view recent posts of fandoms they follow in their feed
    * Users can view their own recent posts in their feed
* Creation
    * Users can create a new text post
    * Users can post to their profile or to a specifc Fandom Hub
* User account
    * User can view their identity and stats
* Search
    * Users can search for Fandom hubs
    * Users can enter a Fandom hub page after search (gallery)
* Fandom Hub Page
    * Users can view a Fandom hub page
    * Users can join a fandom hub

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Feed
* Creation
* Search
* Profile

**Flow Navigation** (Screen to Screen)

* Login Screen
   * Feed
* Register Screen
    * Feed
* Feed Screen
    * None
* Creation Screen
    * Feed (if initially feed/profile tab)
    * Fandom Hub (if initially on Fandom Hub)
* Search Screen
    * Hub gallery screen
* Hub gallery screen
    * Fandom Hub
* Profile Screen
    * none
* Fandom Hub screen
    * none

## Wireframes
<img src="https://i.imgur.com/wIFQRvZ.jpg" width=600>

### [BONUS] Digital Wireframes & Mockups
<img src="https://i.imgur.com/0g6jV26.png" width=100> <img src="https://i.imgur.com/TjgvEZv.png" width=100>
<img src="https://i.imgur.com/jTJQ6PP.png" width=100> <img src="https://i.imgur.com/Lqjn0d9.png" width=100>
<img src="https://i.imgur.com/0iEIfyj.png" width=100> <img src="https://i.imgur.com/d7Mzwwj.png" width=100>
<img src="https://i.imgur.com/Waa4LsX.png" width=100> <img src="https://i.imgur.com/pmQAkDV.png" width=100>

### [BONUS] Interactive Prototype
<img src="https://i.imgur.com/4dhcTP5.gif" width=300>


## Schema

### Models (For MVP->Minimal Viable Product)

User
|   Property   |  Type    |              Description                           |
|--------------|----------|----------------------------------------------------|
| objectId     | String   | unique id for the user object(default)   	       |
| username     | String   | name used for user whilst using app  (required)    |
| password     | String   | used to create/login to account     (required)     | 
| email        | String   | used to create/login to account                    | 
| createdAt    | DateTime | date when user account was created(default)        |
| updatedAt    | DateTime | date when user account was last updated (default)  |
| firstName    | String   | user first name                     	       |  
| lastName     | String   | user last name                      	       |    

Post
|   Property      |          Type             |            Description                    |
|-----------------|---------------------------|-------------------------------------------|
| objectId        | String                    | unique id for the Post object(default)    |  
| createdAt       | DateTime                  | date when post was created(default)       |
| updatedAt       | DateTime                  | date when post was last updated (default) |
| user            | Pointer to User object    | identify creator of post  (required)      |  
| fandome         | Pointer to Fandome object | identify which fandome post belongs to    |   
| body            | String                    | text for post added by User (required)    |

Fandome
|   Property         |  Type    |          Description               		    |
|--------------------|----------|---------------------------------------------------|
| objectId           | String   | unique id for the Fandome object(default)         | 
| createdAt          | DateTime | date when fandome was created(default)            |
| updatedAt          | DateTime | date when fandome was last updated (default) 	    | 
| description        | String   | text description of fandome  (required)           |  
| name               | String   | name of fandome       (required)                  | 
| keyword            | String   | identifies fandome type (ie.movie,tv,book...) (required)    | 

Following
|      Property      |  Type                     |               Description              		  |
|--------------------|---------------------------|------------------------------------------------|
| objectId           | String                    | unique id for the following object(default)    |
| createdAt          | DateTime                  | date when following was created(default)       |
| updatedAt          | DateTime                  | date when following was last updated (default) |  
| user               | Pointer to User object    | identify which user is the follower (required)           |  
| fandome            | Pointer to Fandome object | identify which fandome the user follows  (required)      |    




## Networking
### List of network request by screen (for MVP-> Minimal Viable Product)

* Login screen
    * (Read/GET) Query logged in User object [hit 1 module User]
    ```java
    ParseUser.logInInBackground(username, password,new LogInCallback(){
    @Override
    public void done(ParseUser user, ParseException e) {
    if(e != null){
    // issue with login
    }
    //if login successful then navigate to main activity
    goMainActivity();
    }
    });
    ```	
* Register Screen
    * (Create/Post) Create a new User object [hit 1 module User]
    ```java
    ParseUser user = new ParseUser();
    // Set core properties
    user.setUsername(username);
    user.setPassword(password);
    user.signUpInBackground(new SignUpCallback() {
    @Override
    public void done(ParseException e) {
    if (e != null) {
    //issue with sign up
    }
    //if sign up successful then navigate to main activity
    goMainActivity();
    }
    });
    ```
* Feed
    * (Read/GET) Query the ids of the fandoms the user follows [hit 1 module Following]
    * (Read/GET) Query the post from the fandoms the user follows [hit 1 module Post]
    ```java
    ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
    query.include(user);
    query.include(fandomeid);
    query.addDescendingOrder(Post.KEY_CREATED_AT);
    query.findInBackground(new FindCallback<Post>() {
    @Override
    public void done(List<Post> posts, ParseException e) {
    if(e != null){
    //issue getting posts
    return;
    }
    //successful then iterate through posts
    //notify adapter
    });
    ```
* Creation
    * (Create/POST) Create a new post object [hit 1 module Post]
    ```java
    Post post = new Post();
    post.setDescription(description);
    post.setUser(currentUser);
    post.saveInBackground(new SaveCallback() {
    @Override
    public void done(ParseException e) {
	if(e != null){
	// error whilst saving post
	}
	//success
	// go back to initial screen
	}
    });
    ```
* User account
    *(Read/GET) Query logged in user object [hit 1 module User]
    * (Read/Get) Query all posts the User have created [hit 1 module post]     
    ```java
    ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
    query.include(user);
    query.whereEqualTo(userid, ParseUser.getCurrentUser());
    query.addDescendingOrder(Post.KEY_CREATED_AT);
    query.findInBackground(new FindCallback<Post>() {
    @Override
    public void done(List<Post> posts, ParseException e) {
	if(e != null){
	    // issue getting posts
	}
	//successful then iterate through posts
	for(Post post:posts){
	   //do something
	}
	//notify adapter
    }
    });
    ```
* Search
    * (Read/Get) Query all the matching Fandoms based off search criteria(fandome name) [hit 1 module Fandome]
    ```java
    ParseQuery<Fandom> query = ParseQuery.getQuery("Fandom");
    ParseQuery<ParseUser> userQuery = ParseUser.getQuery();
    userQuery.whereMatchesKeyInQuery("name", fandomName);
    userQuery.findInBackground(new FindCallback<Fandom>() 
    void done(List<Fandome> results, ParseException e) {
    // results has the list relatted fandom to search name
    }
    });
    ```
* Fandom Hub Page
    * (Read/Get) Query the selected Fandom object [hit 1 module Fandome]
    * (Read/Get) Query all the post where belonging to the selected Fandome object [hit 1 module Post]
    ```java
    ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
    query.include(fandom);
    query.whereEqualTo(fandomid, ParseUser.getCurrent());
    query.addDescendingOrder(Post.KEY_CREATED_AT);
    query.findInBackground(new FindCallback<Post>() {
    @Override
    public void done(List<Post> posts, ParseException e) {
	if(e != null){
	    // issue getting posts
	}
	//successful then iterate through posts
	for(Post post:posts){
	   //do something
	}
	//notify adapter
    }
    });
  
  ```
## Build-Progress

<img src="/flix.gif" width=400 height=300>
<img src="https://i.imgur.com/hsG0WIR.gif" width=396 height=649>

