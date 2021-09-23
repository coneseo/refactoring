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