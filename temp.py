import speech_recognition as sr
import win32com.client
r = sr.Recognizer()

speaker = win32com.client.Dispatch("SAPI.SpVoice") 
s = "Only Amazon service is avaiable"
audio = ""
speaker.Speak(s)
while(True):
    try:
        speaker.speak("Command Amazon to start service")
        print("Command Amazon to start service")
        with sr.Microphone() as source:
            audio = r.listen(source)
            print(r.recognize_google(audio))
        if(r.recognize_google(audio) == "Amazon"):
            speaker.Speak(r.recognize_google(audio))
            break
        else:
            speaker.speak("kindly speak Amazone again")
            print("kindly speak Amazone again")
            continue
    except Exception as e:
        speaker.speak("kindly speak Amazone again Exception")
        print("kindly speak Amazone again Exception")
        continue

#speaker.speak("Please speak item you want to add in cart")

while(True):
    speaker.speak("Please speak item you want to add in cart")
    print("Please speak item you want to add in cart")
    try:
        with sr.Microphone() as source:
            audio = r.listen(source)
            print("Okay Thanks...")
        print("You said: " + r.recognize_google(audio))
        with open("E:\Exprmnt\input.txt","w") as file:
            file.write(r.recognize_google(audio))
        break
    except Exception as e:
        speaker.speak("Sorry i did'nt get the item.")
        print("Sorry i did'nt get the item.")
        continue
