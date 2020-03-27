# map_project 
(Aplicatia a fost realizata in IntelliJ IDEA 2019.2.3)
# Enunțul pe scurt
Dezvoltați o aplicație care ajută cadrele didactice să monitorizeze evaluarea temelor de
laborator la disciplina MAP. Aplicația va permite înregistrarea unui feedback oferit studentului
la adaugarea unei note la o anumită tema de laborator. Filtrări și rapoarte cu privire la predarea și evaluarea temelor sunt de
asemenea oferite utilizatorului.

## Atributele necesare fiecărei resurse ar putea fi:
- Student: ID, nume, prenume, grupa, email, cadruDidacticIndrumatorLab (String)
- Tema: ID, descriere, startWeek (Integer-nr saptamanii din cursul semetrului), deadlineWeek
(Integer - numarul saptamanii din cursul sem reprezentand termenul de predare)
- Nota: ID, data, profesor, valoare, unde ID=ID_Student+ID_Tema (un student la o anumita tema
are o singura nota)
- StructuraAnUniversitar: ID, anulUniversitar, sem1: StructuraSemestru, sem2:
StructuraSemestru.

## Cerințe non-funționale:
- Arhitectură Stratificată: repository, service, ui - de tip GUI 
- Pentru layerul de UI, Implementați șablonul MVC. Exemplu: dacă alegeți entitatea
Student, veți avea o clasă StudentView.fxml (fisier resursa) si una StudentController.java
(care încapsulează modelul de date)
- Persistenta datelor: in fisier XML folosind DOM parser. Aplicatia trebuie sa permita incarcarea datelor folosind un fisier de
configurare (fisier resursa). Astfel, o solutie ar putea fi aceasta (sugestie: creati un gradle
project).
- Sablonul Observer: la adaugarea unui student/tema sau nota, acesta/aceasta va putea vi
observata imediat in tabelul corespunzator (O solutie posibila ar putea fi aceasta: Serviceul este un Observable, iar controllerul este un Observer – notifica view-ul sa se updateze)


## Principalele funcționalități

1. Implementarea operațiilor CRUD la alegere dintre entitatea Student sau
Tema. Interfata cu utilizatorul (GUI) trebuie sa permita efectuarea celor patru
operatii CRUD pentru entitatea aleasa (STUDENT sau TEMA).
Constrangeri impuse pentru entitatea Tema (ID, descriere, startWeek, deadlineWeek):
startWeek si deadlineWeek sa aiba valori intregi de la 1 la 14, startWeek &
deadlineWeek ;
- startWeek – reprezinta saptamana cand a fost adaugata tema (se determina
automat (nu se introduce de utilizator) avand data curenta si structura anului
universitar;
- la modificarea termenului de predare pt o temă existentă, deadlineWeek, nr
săptămânii curente trebuie sa fie mai mic sau egal decât nr săptămânii cu
termenul de predare.

2. Rapoarte:
- Nota la laborator pentru fiecare student (media ponderată a notelor de la
temele de laborator; pondere tema=nr de săptămâni alocate temei).
- Cea mai grea tema: media notelor la tema respectivă este cea mai mică.
- Studenții care pot intra în examen (media mai mare sau egală cu 4).
- Studenții care au predat la timp toate temele.


3. Adăugarea unei note: scenarii posibile
- Selectarea temei, dintr-o componentă grafică de tipul combobox; valoarea
default, selectată pentru temă, va fi tema care trebuie predată in
săptămâna curentă. Dacă studentul predă o temă restantă se va selecta
tema respectivă si se vor aplica penalitățile, dacă nu a lipsit motivat. Dacă
a întarziat profesorul să treacă notele, se va selecta tema pe care o predă
studentul si nr de săptămâni de întărziere în predarea temei (dacă a
intarziat); în acest caz data acordării notei va fi o dată din săptămâna
predării temei.
- Căutarea studentului după nume – nu se vor introduce Id-ri
- Adăugare valoare notă (nota propriu-zisă) – într-o componentă grafică de
tipul TextField (cu validarea valorii introduse, să fie numerică).
- Adăugare unui Feedback, într-o componentă de tipul TextArea; dacă
studentul a întârziat în predarea temei se va adauga automat in
componenta grafică pt feedback textul: “NOTA A FOST DIMINUATĂ CU n
PUNCTE DATORITĂ ÎNTÂRZIERILOR”. Ar putea exista feedbak-uri
predefinite iar profesorul doar să le editeze.
- Apăsarea unui buton pentru adaugarea notei va deschide, in prealabil, o
fereastra de confirmare (un fel de preview), ce contie informatii asupra
notei, precum: tema, studentul, valoarea notei, penalizarea (daca e cazul),
sau absent motivat (daca e cazul), iar apoi nota va fi salvata prin apasarea
unui buton OK sau putem anula adaugarea notei, prin actionarea unui
buton CANCEL, din fereastra de confirmare.
- Se vor avea în vedere toate restrictiile referitoare la adăugarea unei note:
unui student la o anumită temă sa va acorda doar o notă, nu se mai poate
acorda nota, la tema respectiva, dacă întârzierea e mai mare de două
săptămâni, dacă a întarziat profesorul să treacă notele.
