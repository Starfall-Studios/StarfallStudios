import random
import datetime
from datetime import timedelta
from random import randrange
from faker import Faker

print("Welcome to the Fallen Legends Answer Generator!")

def generateAnswer():
    print("Generating answer...")
    answer = ""

    fake = Faker()

    answer += "\""
    startDate = datetime.datetime(2023, 1, 30, 0, 0, 0)
    dateTime = fake.date_time_between(start_date=startDate, end_date='now', tzinfo=None)

    answer += str(dateTime.year)
    answer += "/"
    answer += str(dateTime.month)
    answer += "/"
    answer += str(dateTime.day)
    answer += " "
    answer += str(dateTime.hour)
    answer += ":"
    answer += str(dateTime.minute)
    answer += ":"
    answer += str(dateTime.second)
    answer += "\",\""

    choice = random.randint(0, 3)
    if choice == 0:
        answer += "Menos de 18"
    elif choice == 1:
        answer += "Entre 18 y 25"
    elif choice == 2:
        answer += "Entre 26 y 35"
    elif choice == 3:
        answer += "Mas de 35"
    answer += "\",\""

    isPlayer = random.randint(0, 7)
    if isPlayer == 0:
        answer += "No"
    else:
        answer += "Si"

    answer += "\",\""

    if isPlayer == 0:
        choice = 0
    else:
        choice = random.randint(1, 3)

    if choice == 0:
        answer += "Nunca"
    elif choice == 1:
        answer += "Alguna vez"
    elif choice == 2:
        answer += "Habitualmente"
    elif choice == 3:
        answer += "Varias horas al dia"

    answer += "\",\""

    choice = random.randint(0, 4)
    if choice == 0:
        answer += "Estilo Clash of Clans"
    elif choice == 1:
        answer += "Estilo Clash Royale"
    elif choice == 2:
        answer += "Estilo Pokemon Go"
    elif choice == 3:
        answer += "Casuales"
    elif choice == 4:
        answer += "Otros"

    answer += "\",\""

    if random.randint(0, 1) == 0:
        answer += "Si"
    else:
        answer += "No"

    answer += "\",\""

    if random.randint(0, 1) == 0:
        answer += "Si"
    else:
        answer += "No"

    answer += "\",\""

    playedPokemonGo = random.randint(0, 1)
    if playedPokemonGo == 0:
        answer += "Si"
    else:
        answer += "No"

    answer += "\",\""

    choice = random.randint(0, 4)
    if playedPokemonGo == 1:
        choice = 4
    else:
        choice = random.randint(0, 3)

    if choice == 0:
        answer += "Caminar para conseguir los objetivos"
    elif choice == 1:
        answer += "Batallas entre criaturas"
    elif choice == 2:
        answer += "Coleccion de criaturas"
    elif choice == 3:
        answer += "Intercambio y relacion con otros jugadores"
    elif choice == 4:
        answer += "No he jugado"

    answer += "\""

    return answer

with open("formAnswers.csv", "a") as file:
    for i in range(0, 70):
        file.write(generateAnswer() + "\n")