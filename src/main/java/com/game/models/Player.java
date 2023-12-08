package main.java.com.game.models;

public class Player implements Comparable<Player>{
    
        String name ;
        int score ;


        public Player() {

        }


        public Player(String name, int score) {
            this.name = name;
            this.score = score;
        }


        public String getName() {
            return name;
        }


        public void setName(String name) {
            this.name = name;
        }


        public int getScore() {
            return score;
        }


        public void setScore(int score) {
            this.score = score;
        }


        @Override
        public int compareTo(Player arg) {
            if ( arg.getScore() > this.score ) {
                return -1 ;
            }
            else if ( arg.getScore() < this.score ) {
                return 1 ;
            }
            else {
                return this.name.compareTo(arg.getName());
            }
        }

        
        
}
