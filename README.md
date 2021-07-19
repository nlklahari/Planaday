Original App Design Project
===

# Planaday

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description
Allow the app to build a schedule to enjoy a day based on user preferences. 

### App Evaluation
[Evaluation of your app across the following attributes]
- **Category:** Lifestyle
- **Mobile:** Mobile allows for location tracking and allows users to make quick bookings. 
- **Story:** Users can choose a set of preferences to plan a day of activities.
- **Market:** Everyone looking to spend their time well.
- **Habit:** Using the app doesn’t become habitual but can be used more often depending on the user’s interest in engaging in different activities.
- **Scope:** Basic version would allow users to choose distance, duration, group/individual setting, and environment preferences. This version will also include features such as viewing a list of activities structured as a schedule and link options for overnight stays and travel. Next version will allow users to add others to a plan and utilize the preferences of everyone to generate a schedule.
## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* Users can create a new account.
* Users can login.
* Users can set basic preferences for plan. (duration, min/max distance,min/max price, group/individual setting, indoor/outdoor)
* Users can name their plans.
* Users can view a plan.
* Users can save plans.
* User can view each event, its duration, location (if there is one), and estimated expenses for every plan.
* Users can view thier saved plans.
* Users can set advanced preferences for plan. (crowd size, category of events (i.e music, dance), mood (i.e active, chill))
* Users can view a calendar with their plans.
* Users can access their plans from the calendar.


**Optional Nice-to-have Stories**

* Users can edit the plan after plan creation.
* Users can share their plan on messaging apps.
* Users are directed to google maps app when they click on location.
* Users can generate a random plan with minimal preferences set.
* Users get suggestions for activities.
* Users can preset preferences relating to their interests.
* Users can search through their plans. 
* Users can conduct bookings by getting redirected to website from app.
* Users can view their past plans.

### 2. Screen Archetypes

* Login Screen
  * Users can login.
* Account Creation Screen
  * Users can create a new account.
* Saved Plans Screen (Default Screen after login)
  * Users can view thier saved plans.
* Plan Screen
  * Users can name their plans.
  * Users can set basic preferences for plan. (duration, min/max distance,min/max price, group/individual setting, indoor/outdoor)
  * Users can save plans.
* Advanced Preferences Screen
  * Users can set advanced preferences for plan. (crowd size, category of events (i.e music, dance), mood (i.e active, chill))
* Calendar Screen
  * Users can view a calendar with their plans.
  * Users can access their plans from the calendar.
* Profile Screen
  * Users can view their past plans.
* Plan Details Screen
  * User can view each event, its duration, location (if there is one), and estimated expenses for every plan. 

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Saved Plans (Main Screen)
 * Calendar
 * Profile
 * Plan Details
* Plan
 * Advanced Preferences 
 * Plan Details

**Flow Navigation** (Screen to Screen)

* Login Screen
 * Saved Plans (Main Screen)
* Account Creation Screen 
 * Saved Plans (Main Screen)
* Saved Plans (Main Screen)
 * Calendar
 * Profile
 * Plan Details
* Plan
 * Advanced Preferences 
 * Plan Details
* Calendar
 * Plan Details

## Wireframes
[Add picture of your hand sketched wireframes in this section]
<img src="https://github.com/nlklahari/Planaday/blob/main/Screen%20Shot%202021-07-12%20at%209.22.57%20AM.png" width=600>

### [BONUS] Digital Wireframes & Mockups
|      |      |      | | |. |
|------------|-------------|------------- | -------------|-- | -- |
| <img src="https://github.com/nlklahari/Planaday/blob/main/Screen%20Shot%202021-07-12%20at%209.27.48%20AM.png" width="250"> | <img src="https://github.com/nlklahari/Planaday/blob/main/Screen%20Shot%202021-07-12%20at%209.28.09%20AM.png" width="250"> | <img src="https://github.com/nlklahari/Planaday/blob/main/Screen%20Shot%202021-07-12%20at%209.28.27%20AM.png" width="250"> | <img src="https://github.com/nlklahari/Planaday/blob/main/Screen%20Shot%202021-07-12%20at%209.28.45%20AM.png" width="250"> | <img src="https://github.com/nlklahari/Planaday/blob/main/Screen%20Shot%202021-07-12%20at%209.29.06%20AM.png" width="250"> | <img src="https://github.com/nlklahari/Planaday/blob/main/Screen%20Shot%202021-07-12%20at%209.29.21%20AM.png" width="250"> |



### [BONUS] Interactive Prototype

## Schema 
### Models
User Model
| Property | Type     | Descripton |
| -------- | -------- | ---------- |
| objectID     | String     | unique id for the user plan (default field)       |
| createdAt     | DateTime     | udate when post is created (default field)       |
| updatedAt     | DateTime     | date when post is last updated (default field)       |
| Name     | String     | name of user      |
| Email     | String     |email of user      |
| Username     | String     | username of user      |
| Password     | String     | password of user      |

Plan Model
| Property | Type     | Descripton |
| -------- | -------- | ---------- |
| objectID     | String     | unique id for the user plan (default field)       |
| createdAt     | DateTime     | udate when post is created (default field)       |
| updatedAt     | DateTime     | date when post is last updated (default field)       |
| author     | Pointer to User    | user who created the plan      |
| events     | Json Array     | array of events for one plan    |
| duration | Double | amount of time for entire plan |
| date | DateTime | scheduled date and time for event |

Event Model
| Property | Type     | Descripton |
| -------- | -------- | ---------- |
| objectID     | String     | unique id for the user plan (default field)       |
| createdAt     | DateTime     | udate when post is created (default field)       |
| updatedAt     | DateTime     | date when post is last updated (default field)       |
| name     | String    | name of event      |
| duration     | Double     | amount of time for event    |
| environment | String | indoor or outdoor activity |
| setting | String | group or individual activity |
| price | Double | estimated cost of the event |
| distance | Double | estimated distance from user's current location |
| location | String | location of the event |

### Networking

* Saved Plan Screen
    * (GET) query all plans that user created
    * (Delete) delete plan once current date has passed the scheduled date
* Plan Screen
    * (POST) send preferences for plan
* Plan Detials Screen
    * (GET) get plan from set preferences
    * (Update) append new plan to users list of plans
* Calendar Screen
    * (GET) query all plans that user created
- [Create basic snippets for each Parse network request]
- [OPTIONAL: List endpoints if using existing API such as Yelp]
