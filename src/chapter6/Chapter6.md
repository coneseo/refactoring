###메서드 추출
어떤 코드를 그룹으로 묶어도 되겠다고 판단될 땐
그 코드를 빼내어 목적을 잘 나타내는 직관적 이름의 메서드를 만들자.


~~~
    void printOwing(double amount){
        printBanner();
    
        // 세부 정보 출력
        System.out.println("name: "+ _name);
        System.out.println("amount: "+ amount);
    }
~~~

~~~
    void printOwing(double amount) {
        printBanner();
        printDetailis(amount);       
    }

    void printDetails(double amount) {
        System.out.println("name: " + _name);
        System.out.println("amount" + amount):
    }
~~~
동기
메서드 추출 기법은 제일 많이 사용된다. 메서드가 너무 길거나 코드에 주석을 달아야만 의도를 이해할 수 있을 때,
나는 그 코드를 빼내어 별도의 메서드를 만든다. 

~~~
    void printOwing(){
        Enumeration e = _orders.elements();
        double outstanding = 0.0;
        System.out.println("*********")
        System.out.println("*****고객 외상****")
        System.out.println("*********")
    }

    while (e.hasMoreElements()){
        Order each = (Order) e.nextElements();
        outstanding += each.getAmount();
    }

    System.out.println("고객명: "+_name);
    System.out.println("외상액: "+outstanding);
~~~

~~~
    void printOwing(){
        Enumeration e = _orders.elements();
        double outstanding = 0.0;

        while (e.hasMoreElements()){
        Order each = (Order) e.nextElements();
        outstanding += each.getAmount();
        }
    }   
~~~
예제: 지역변수 사용
지역변수 문제 중 제일 가벼운 경우는 지역변수가 읽히기만 하고 변경되지 않을 때다. 이럴 땐
지역변수를 그냥 매개변수로 전달하면 된다.
