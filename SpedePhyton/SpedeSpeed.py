import RPi.GPIO as GPIO
import time
import random
import requests



def chooseRandColor():
    valinta = random.choice(["blue", "green", "yellow", "red"])
    return valinta

def getLed(givenColor):
    if givenColor == "blue":
        GPIO.output(led1, GPIO.HIGH)
        
    if givenColor == "green":
        GPIO.output(led2, GPIO.HIGH)
        
    if givenColor == "yellow":
        GPIO.output(led3, GPIO.HIGH)
        
    if givenColor == "red":
        GPIO.output(led4, GPIO.HIGH)
        
def resetLeds():
    GPIO.output(led1, GPIO.LOW)
    GPIO.output(led2, GPIO.LOW)
    GPIO.output(led3, GPIO.LOW)
    GPIO.output(led4, GPIO.LOW)

def getButtonInput():
    if GPIO.input(button1) == False:
        return("blue")
    
    if GPIO.input(button2) == False:
        return("green")
        
    if GPIO.input(button3) == False:
        return("yellow")
        
    if GPIO.input(button4) == False:
        return("red")
    else:
        return("empty")

    
    

def ledTest():
    time.sleep(0.1)      
    GPIO.output(led1, GPIO.LOW)
    GPIO.output(led2, GPIO.LOW)
    GPIO.output(led3, GPIO.LOW)
    GPIO.output(led4, GPIO.LOW)

    
    time.sleep(0.1)
    GPIO.output(led1, GPIO.HIGH)
    GPIO.output(led2, GPIO.HIGH)
    GPIO.output(led3, GPIO.HIGH)
    GPIO.output(led4, GPIO.HIGH)
    
    

led1 = 12 #blue
led2 = 13 #green
led3 = 16 #yellow
led4 = 17 #red

button1 = 21 #blue
button2 = 20 #green
button3 = 19 #yellow
button4 = 18 #red

score = 0

#led setup
GPIO.setmode(GPIO.BCM)
GPIO.setup(led1, GPIO.OUT)
GPIO.setup(led2, GPIO.OUT)
GPIO.setup(led3, GPIO.OUT)
GPIO.setup(led4, GPIO.OUT)

#button setup
GPIO.setup(button1, GPIO.IN, pull_up_down=GPIO.PUD_UP) 
GPIO.setup(button2, GPIO.IN, pull_up_down=GPIO.PUD_UP) 
GPIO.setup(button3, GPIO.IN, pull_up_down=GPIO.PUD_UP)
GPIO.setup(button4, GPIO.IN, pull_up_down=GPIO.PUD_UP)

GPIO.output(led1, GPIO.LOW)
GPIO.output(led2, GPIO.LOW)
GPIO.output(led3, GPIO.LOW)
GPIO.output(led4, GPIO.LOW)




def playRound(setTime):
    timeri = setTime


    valittuvari = chooseRandColor()    
    getLed(valittuvari)
    nappivalinta = "empty"

    while timeri > 0 and nappivalinta == "empty":
        nappivalinta = getButtonInput() 
        timeri = timeri - 0.01
        time.sleep(0.01)

    if valittuvari == nappivalinta:
        resetLeds()
        time.sleep(0.2)
        return 1
        
    
    if valittuvari != nappivalinta:
        resetLeds()
        return 0
    print(nappivalinta)




def main():
    
    uusikierros = True
    
    while uusikierros == True:
    
        print(" _   _                                       _ _ ")
        print("| \ | |                                     | (_)")
        print("|  \| | ___  _ __   ___ _   _ ___ _ __   ___| |_ ")
        print("| . ` |/ _ \| '_ \ / _ \ | | / __| '_ \ / _ \ | |")
        print("| |\  | (_) | |_) |  __/ |_| \__ \ |_) |  __/ | |")
        print("|_| \_|\___/| .__/ \___|\__,_|___/ .__/ \___|_|_|")
        print("            | |                  | |             ")
        print("            |_|                  |_|             ")
    
    
    
    
    
    
        score = 0
        rounds = 0
        lastround = 100
        roundTime = 5
    
        while rounds < lastround:
    
            result = playRound(roundTime)
        
            if result == 1:
                score = 1+score
            else:
                rounds = lastround
            time.sleep(0.1)
            rounds = rounds+1
            roundTime = roundTime - 0.05 
        
        
        print("Sait",score,"pistettä!")
        playerName = input("Anna nimesi ")
    
        requests.post('http://ec2-18-206-35-254.compute-1.amazonaws.com:4000/Spede/add', json={'nimi': playerName, 'pisteet': score})
    
        print("Uusi kierros?")
        jatka = input("Lopeta kaskylla stop, paina mita tahansa painiketta aloittaaksesi uuden pelin ")
        if jatka == "stop":
            uusikierros = False
            


    
main()
GPIO.cleanup()    
    
    

    
    
    
        