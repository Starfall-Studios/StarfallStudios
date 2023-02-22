import random
import datetime

print("Welcome to the Fallen Legends Answer Generator!")

def generateAnswer():
    print("Generating answer...")
    answer = ""

    answer += "\""
    dateTime = datetime.datetime.now()
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
    answer += " p. m. CET\",\""

    answer += str(random.randint(14, 39))
    answer += "\",\""

    if random.randint(0, 1) == 0:
        answer += "Sí"
    else:
        answer += "No"

    answer += "\",\""

    choice = random.randint(0, 3)
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
        answer += "Colección de criaturas"
    elif choice == 3:
        answer += "Intercambio y relación con otros jugadores"
    elif choice == 4:
        answer += "No he jugado"

    answer += "\""

    return answer

with open("formAnswers.csv", "a") as file:
    file.write("Fecha,Edad,¿Juegas a videojuegos?,¿Con qué frecuencia?,¿Qué tipo de videojuegos te gustan?,¿Te gustaría que tuvieramos un videojuego?,¿Te gustaría que tuvieras un videojuego propio?,¿Has jugado a Pokemon Go?,¿Qué te gustaba más de Pokemon Go?")

    for i in range(0, 70):
        file.write(generateAnswer() + "\n")