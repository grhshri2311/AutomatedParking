import cv2
cap = cv2.VideoCapture('cars.mp4')  #Path to footage
car_cascade = cv2.CascadeClassifier('cars.xml')  #Path to cars.xml
bicycle_cascade= cv2.CascadeClassifier('two_wheeler.xml') #Path to bicycle.xml
while True:
    ret, img = cap.read()
    gray = cv2.cvtColor(img,cv2.COLOR_BGR2GRAY)
    cars=car_cascade.detectMultiScale(gray,1.8,2)
    bicycle = bicycle_cascade.detectMultiScale(gray, 1.8, 2)
    #Drawing rectangles on detected cars
    for (x,y,w,h) in cars:
       cv2.rectangle(img,(x,y),(x+w,y+h),(225,0,0),2)
       cv2.putText(img, 'Car', (x, y - 10), cv2.FONT_HERSHEY_SIMPLEX, 0.7, (255, 255, 255), 2)
       plate = img[y:y + h, x:x + w]
       cv2.imshow('Car', plate)
    # Drawing rectangles on detected bicycles
    for (x,y,w,h) in bicycle:
        cv2.rectangle(img,(x,y),(x+w,y+h),(0,0,255),2)
        cv2.putText(img, 'Bike', (x, y - 10), cv2.FONT_HERSHEY_SIMPLEX, 0.7, (255, 255, 255), 2)
        plate = img[y:y + h, x:x + w]
        cv2.imshow('Bike', plate)

    cv2.imshow('img',img) #Shows the frame
    if cv2.waitKey(20) & 0xFF == ord('q'):
        break
cap.release()
cv2.destroyAllWindows()