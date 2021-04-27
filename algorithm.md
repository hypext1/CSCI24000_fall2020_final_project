Card:
abstract class
holds and returns cost and name

Creature:
extends card
holds and erturns power

Spell:
extends card

Equipment:
interface
3rd type of card, future expansion

Deck:
linked list
holds 12 cards
has a "draw" function to return a random card, removing it from hand

Hand:
linked list
starts with drawing 4 from deck
has a "hdraw" function to draw a card from deck
has a "play" function to play a card, reomving it form hand

Game:
holds board
instantiates hand, deck, creatures, and spells
displays board
allows players to play creatures/spells
makes creatures fight
checks if a player has won
holds a database of spells for users to cast
