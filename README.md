Correcthorse: a program to generate strong passwords from wordlists

Written in Java 8 for Linux-systems by Antti Kosonen. 
Licence: GNU General Public License v2.0

Weak passwords are a major problem on the internet today, and with 
increasing processing power the problem is only going to get worse.
There is a way to formulate passwords that are easy for humans to
remember and hard for computers to guess, as XKCD described years
ago: https://www.xkcd.com/936/

There already are some apps to generate good passwords, but they all
are missing some functionality that I think is central. Users should
be able to use their own wordlists - this way they can generate
passwords in their own language, and even dialect. They can choose 
words words that are familiar to them, but unfamiliar to others
(like words connected with their hobbies, names of local places
etc.)

I wrote this program to try to answer these challenges. The user
can load custom lists of words and use them to generate passwords.
Also blacklists can be added to list words that are excluded from
passwords. Lists of common passwords should be used for this 
purpose. Lists can also be removed.

This is a work in process. Later versions will include a GUI, the
ability to use custom rules for password generation, a calculator
to calculate entropy of the generated passwords, and others.
