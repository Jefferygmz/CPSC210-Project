# The Medal Board
The application would let users **add** and **keep track** the number of gold, silver, or bronze medals of players
or teams. Users have the options to change the board between a _player_ view and a _team_ view, 
which would summarize medal counts of the players or teams.

## User Groups
Users can be schools, companies, organizations or even countries' sports meeting organizer. 
They can use this application to keep track the medal counts of players and teams 
when they are holding a sports meeting.

## Reason to make this program
This program is interested to me because, first I love sports, 
second I'm amazed by people's passions and hardworking in sports meetings,
third I want more people to feel the joy and passion from sports competitions.
I'd like to do a little contribution to help people to organize a sports meeting easier, 
which may motivate people to hold more sports meetings,
and then achieve my goal, to make more people feel the happiness and passion of sports and competition.

### User Stories:
- As a user, I want to be able to create new players
- As a user, I want to be able to add a gold medal to a selected player
- As a user, I want to be able to add a silver medal to a selected player
- As a user, I want to be able to add a bronze medal to a selected player
- As a user, I want to be able to show the number of gold medals of a selected player
- As a user, I want to be able to show the number of silver medals of a selected player
- As a user, I want to be able to show the number of bronze medals of a selected player
- As a user, I want to be able to add a player to the list of players
- As a user, I want to be able to create new teams
- As a user, I want to be able to add a player to a team of players
- As a user, I want to be able to add a team to the list of teams
- As a user, I want to be able to select a team and view a list of players belong to that team with their medal counts
- As a user, I want to be able to have the options to change the view of the medal board
  between player board and team board
- As a user, I want to be able to save the medal board I created
- As a user, I want to be able to continue from where I left off(where the user has saved)

## Phase 4: Task 2
Thu Mar 31 22:48:27 PDT 2022
Created a new team with name Canada.
Thu Mar 31 22:48:27 PDT 2022
Added team Canada to the team list.
Thu Mar 31 22:48:35 PDT 2022
Added player Jack to the player list.
Thu Mar 31 22:48:35 PDT 2022
Added player Jack to team Canada.
Thu Mar 31 22:48:45 PDT 2022
Created a new team with name France.
Thu Mar 31 22:48:45 PDT 2022
Added player Frank to the player list.
Thu Mar 31 22:48:45 PDT 2022
Added player Frank to team France.
Thu Mar 31 22:48:45 PDT 2022
Added team France to the team list.
Thu Mar 31 22:48:57 PDT 2022
Added player Pizza to the player list.
Thu Mar 31 22:48:57 PDT 2022
Added player Pizza to team France.
Thu Mar 31 22:49:00 PDT 2022
Added a gold to player Jack.
Thu Mar 31 22:49:03 PDT 2022
Added a silver to player Frank.
Thu Mar 31 22:49:07 PDT 2022
Added a bronze to player Pizza.
Thu Mar 31 22:49:18 PDT 2022
Saved the file to ./data/medalboard.json.

## Phase 4: Task 3
I would extract a class from the Player class and the Team class, then let these two class extends the extracted class. 
Both of these two classes got very similar fields, name, gold counts, silver counts, and bronze counts. Therefore, they
got many similar methods, for example, the getter for getting the name and medal counts. By extracting a new class, the
code's duplication can be decreased.

I can also extract a class from AddPlayerPanel and AddTeamPanel class. They can share the same JFrame and same buttons.
Their difference is the number of text fields, which can be separately implemented by extract a class from these two then
extends the extracted class. By doing so, the duplication can be decreased.