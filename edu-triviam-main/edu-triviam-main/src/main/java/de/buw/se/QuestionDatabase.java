
package de.buw.se;

import java.util.ArrayList;
import java.util.List;

public class QuestionDatabase {
    private List<Question> questions;

    public QuestionDatabase() {
        questions = new ArrayList<>();
        //loadQuestions();
    }
    
    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public void loadQuestions(String subject,String difficulty) {
    	questions.clear();
    	if (subject.equals("General Knowledge") && difficulty.equals("Easy")) {
            
            questions.add(new Question("What is the largest planet in our solar system?",
                    new String[]{"a. Earth", "b. Mars", "c. Jupiter", "d. Saturn"},
                    "c. Jupiter"));
            
            questions.add(new Question("Who was the first President of the United States?",
                    new String[]{"a. Abraham Lincoln", "b. Thomas Jefferson", "c. George Washington", "d. John Adams"},
                    "c. George Washington"));
            
            questions.add(new Question("What color are bananas when they are ripe?",
                    new String[]{"a. Green", "b. Yellow", "c. Red", "d. Purple"},
                    "b. Yellow"));
            
            questions.add(new Question("How many continents are there on Earth?",
                    new String[]{"a. Five", "b. Six", "c. Seven", "d. Eight"},
                    "c. Seven"));
            
            questions.add(new Question("What is the name of Mickey Mouse’s dog?",
                    new String[]{"a. Pluto", "b. Goofy", "c. Donald", "d. Max"},
                    "a. Pluto"));
            
            questions.add(new Question("What is the capital of France?",
                    new String[]{"a. London", "b. Paris", "c. Rome", "d. Berlin"},
                    "b. Paris"));
           
           questions.add(new Question("What is the tallest mountain in the world?",
                   new String[]{"a. Mount Kilimanjaro", "b. Mount Everest", "c. Mount Fuji", "d. Mount McKinley"},
                   "b. Mount Everest"));
           
           questions.add(new Question("Which planet is known as the 'Red Planet'?",
                   new String[]{"a. Venus", "b. Mars", "c. Jupiter", "d. Saturn"},
                   "b. Mars"));
           
        } else if (subject.equals("General Knowledge") && difficulty.equals("Medium")) {

           questions.add(new Question("Who invented the telephone?",
        	        new String[]{"a. Alexander Graham Bell", "b. Thomas Edison", "c. Nikola Tesla", "d. Albert Einstein"},
        	        "a. Alexander Graham Bell"));
           
           questions.add(new Question("Who is the current President of the United States?",
                   new String[]{"a. Barack Obama", "b. Joe Biden", "c. Donald Trump", "d. George W. Bush"},
                   "b. Joe Biden"));
        	
          
           questions.add(new Question("What is the largest desert in the world?",
                   new String[]{"a. Sahara", "b. Gobi", "c. Kalahari", "d. Arabian"},
                   "a. Sahara"));


           questions.add(new Question("Who was the first man to walk on the moon?",
                   new String[]{"a. Neil Armstrong", "b. Buzz Aldrin", "c. Yuri Gagarin", "d. Michael Collins"},
                   "a. Neil Armstrong"));

            questions.add(new Question("What is the main ingredient in guacamole?",
                    new String[]{"a. Tomato", "b. Onion", "c. Avocado", "d. Pepper"},
                    "c. Avocado"));
            
            questions.add(new Question("Who painted the Mona Lisa?",
                    new String[]{"a. Pablo Picasso", "b. Vincent van Gogh", "c. Leonardo da Vinci", "d. Michelangelo"},
                    "c. Leonardo da Vinci"));
            
            questions.add(new Question("What is the largest ocean on Earth?",
                    new String[]{"a. Pacific Ocean", "b. Atlantic Ocean", "c. Indian Ocean", "d. Arctic Ocean"},
                    "a. Pacific Ocean"));
            
            questions.add(new Question("What is the hardest natural substance on Earth?",
                    new String[]{"a. Diamond", "b. Quartz", "c. Graphite", "d. Topaz"},
                    "a. Diamond"));
            
        	
        } else if (subject.equals("General Knowledge") && difficulty.equals("Hard")) {
        	
        	 questions.add(new Question("What is the smallest country in the world?",
                     new String[]{"a. Monaco", "b. Vatican City", "c. San Marino", "d. Liechtenstein"},
                     "b. Vatican City","It is also known as the Holy See"));
        	
        	questions.add(new Question("In which year did the Titanic sink?",
                    new String[]{"a. 1908", "b. 1914", "c. 1920", "d. 1912"},
                    "d. 1912","The Republic of China was established in this year"));

            
        	questions.add(new Question("Which country has the longest coastline in the world?",
                    new String[]{"a. Russia", "b. Canada", "c. Australia", "d. Indonesia"},
                    "b. Canada","World's largest producer of maple syrup"));

        	questions.add(new Question("Who was the Greek god of war?",
                    new String[]{"a. Zeus", "b. Hades", "c. Apollo", "d. Ares"},
                    "d. Ares","The Greek deity who is typically depicted carrying weapons such as a spear, sword, or shield"));

        	questions.add(new Question("In what year was the United Nations established?",
                    new String[]{"a. 1943", "b. 1945", "c. 1947", "d. 1950"},
                    "b. 1945","End of World War II"));

            questions.add(new Question("What is the currency of Japan?",
                     new String[]{"a. Yuan", "b. Won", "c. Yen", "d. Ringgit"},
                     "c. Yen","This currency's name rhymes with 'hen'"));

        	questions.add(new Question("Who developed the theory of relativity?",
                    new String[]{"a. Isaac Newton", "b. Albert Einstein", "c. Galileo Galilei", "d. Niels Bohr"},
                    "b. Albert Einstein","Renowned scientist often depicted with understanding of nuclear physics(mass energy equivalence)"));

        	questions.add(new Question("Which gas is most abundant in Earth's atmosphere?",
                    new String[]{"a. Oxygen", "b. Nitrogen", "c. Carbon Dioxide", "d. Hydrogen"},
                    "b. Nitrogen","Its atomic number is 7"));

            
        	
        } else if (subject.equals("Geography") && difficulty.equals("Easy")) {
        	
        	questions.add(new Question("What is the capital of Australia?",
                    new String[]{"a. Sydney", "b. Canberra", "c. Melbourne", "d. Brisbane"},
                    "b. Canberra"));
        	questions.add(new Question("What is the capital of France?",
                    new String[]{"a. London", "b. Paris", "c. Rome", "d. Berlin"},
                    "b. Paris"));
        	
        	questions.add(new Question("What is the capital of Italy?",
                    new String[]{"a. Rome", "b. Venice", "c. Milan", "d. Naples"},
                    "a. Rome"));
        	
        	questions.add(new Question("Which country is known as the Land of the Rising Sun?",
                    new String[]{"a. China", "b. Japan", "c. Korea", "d. Thailand"},
                    "b. Japan"));

        	questions.add(new Question("What river runs through Egypt?",
                    new String[]{"a. Amazon", "b. Nile", "c. Yangtze", "d. Mississippi"},
                    "b. Nile"));
        	
        	questions.add(new Question("In which country can you find the island of Bali?",
                    new String[]{"a. Thailand", "b. Indonesia", "c. Malaysia", "d. Philippines"},
                    "b. Indonesia"));
        	questions.add(new Question("Which continent is the Sahara Desert located in?",
                    new String[]{"a.  Asia", "b. Africa", "c. South America", "d. Australia"},
                    "b. Africa"));

        	questions.add(new Question("What is the capital of Canada?",
                    new String[]{"a. Toronto", "b. Vancouver", "c. Ottawa", "d. Montreal"},
                    "c. Ottawa"));
        	
        	
        } else if (subject.equals("Geography") && difficulty.equals("Medium")) {

        	
        	questions.add(new Question("What is the longest river in the world?",
                    new String[]{"a. Amazon", "b. Nile", "c.  Mississippi", "d. Yangtze"},
                    "b. Nile"));
        			
        	questions.add(new Question("Which country has the most natural lakes?",
                    new String[]{"a. Canada", "b. USA", "c. Brazil", "d. India"},
                    "a. Canada"));
        	
        	questions.add(new Question("What is the smallest continent by land area?",
                    new String[]{"a. Antarctica", "b. Europe", "c. Australia", "d. South America"},
                    "c. Australia"));
        	
        	questions.add(new Question("What ocean is on the east coast of the United States?",
                    new String[]{"a. Pacific", "b. Atlantic", "c. Indian", "d. Arctic"},
                    "b. Atlantic"));
        	
        	questions.add(new Question("Which of one of these countries are in South East Asia?",
                    new String[]{"a. New Zealand", "b. China", "c. Bangladesh", "d. Laos"},
                    "d. Laos"));
        	
        	questions.add(new Question("Which country is the Taj Mahal located in?",
                    new String[]{"a. Pakistan", "b. Bangladesh", "c. Nepal", "d. India"},
                    "d. India"));
        	
        	questions.add(new Question("In which country is the Great Barrier Reef located?",
                    new String[]{"a. Australia", "b. Brazil", "c. India", "d. Mexico"},
                    "a. Australia"));

        	questions.add(new Question("How many countries are in the United Kingdom?",
                    new String[]{"a. Six", "b. Three", "c. Four", "d. Five"},
                    "c. Four"));

        } else if (subject.equals("Geography") && difficulty.equals("Hard")) {

        	questions.add(new Question("Which European country has the highest population?",
                    new String[]{"a. France", "b. Germany", "c. United Kingdom", "d. Italy"},
                    "b. Germany","Known for its economic process and significant industrial history"));
        	
        	questions.add(new Question("What is the official language of Brazil?",
                     new String[]{"a. Spanish", "b. Portuguese", "c. English", "d. French"},
                     "b. Portuguese","Speakers of this language are called lusophones"));
        	
        	questions.add(new Question("Which U.S. state has the most volcanoes?",
                     new String[]{"a. California", "b. Alaska", "c. Hawaii", "d. Washington"},
                     "b. Alaska","Northernmost state known for its vast wilderness and icy landscapes"));
        	
        	questions.add(new Question("What is the name of the mountain range that separates Europe and Asia?",
                    new String[]{"a. Himalayas", "b. Andes", "c. Rockies", "d. Ural"},
                    "d. Ural","hint-geographical barrier often referred to as the 'Gateway to Asia'"));
        	
        	questions.add(new Question("What is the largest country in Africa by land area?",
                    new String[]{"a. Nigeria", "b. Ethiopia", "c. Algeria", "d. Egypt"},
                    "c. Algeria","The North African nation known for its vast Sahara Desert and diverse geography."));
        	
        	questions.add(new Question("Which of these cities are not in England?",
                    new String[]{"a. Edinburgh", "b. London", "c. Manchester", "d. Birmingham"},
                    "a. Edinburgh","Alexander Graham Bell, the inventor of the telephone was born here in 1847"));
        	
        	questions.add(new Question("What is the capital of Mauritius?",
                    new String[]{"a. Port Moresby", "b. Port Louis", "c. Port-au-Prince", "d. Port of Spain"},
                    "b. Port Louis","The name rhymes with 'Short Lewis'"));
        	
        	questions.add(new Question("The Hunua ranges are located in ________",
                    new String[]{"a. Australia", "b. New Zealand", "c. Canada", "d. South Africa"},
                    "b. New Zealand","The national bird of this country is the kiwi"));
        	
        } else if (subject.equals("Maths") && difficulty.equals("Easy")) {
        	questions.add(new Question("What is 10 + 5?",
                    new String[]{"a. 12", "b. 15", "c. 18", "d. 20"},
                    "b. 15"));
        	
        	questions.add(new Question("What is the value of 7 - 3?",
                    new String[]{"a. 3", "b. 4", "c. 5", "d. 6"},
                    "b. 4"));
        	
        	questions.add(new Question("What is the product of 6 and 9?",
                    new String[]{"a. 54", "b. 56", "c. 58", "d. 60"},
                    "a. 54"));
        	
        	questions.add(new Question("What is 100 divided by 4?",
                    new String[]{"a. 20", "b. 25", "c. 30", "d. 40"},
                    "b. 25"));
        	
        	questions.add(new Question("What is the square of 5?",
                    new String[]{"a. 15", "b. 20", "c. 25", "d. 30"},
                    "c. 25"));
        	
        	questions.add(new Question("What is 2 + 2?",
                    new String[]{"a. 3", "b. 4", "c. 5", "d. 6"},
                    "b. 4"));
        	
        	questions.add(new Question("How many sides does a square have?",
                    new String[]{"a. 3", "b. 4", "c. 5", "d. 6"},
                    "b. 4"));
        	
        	questions.add(new Question("What is the product of 50 and 8?",
                    new String[]{"a. 300", "b. 42", "c. 400", "d. 58"},
                    "c. 400"));
        	
        } else if (subject.equals("Maths") && difficulty.equals("Medium")) {
        	
        	questions.add(new Question("What is the value of x if 2x + 3 = 7?",
                    new String[]{"a. 1", "b. 2", "c. 3", "d. 4"},
                    "b. 2"));
        	
        	questions.add(new Question("What is the perimeter of a rectangle with length 8 and width 3?",
                    new String[]{"a. 16", "b. 18", "c. 24", "d. 22"},
                    "d. 22"));
        	
        	questions.add(new Question("If y = 3x and x = 4, what is the value of y?",
                    new String[]{"a. 7", "b. 10", "c. 12", "d. 15"},
                    "c. 12"));
        	
        	questions.add(new Question("What is the area of a triangle with base 5 and height 6?",
                    new String[]{"a. 15", "b. 12", "c. 10", "d. 30"},
                    "a. 15"));
        	
        	questions.add(new Question("What is the value of 2^5?",
                    new String[]{"a. 10", "b. 16", "c. 64", "d. 32"},
                    "d. 32"));
        	
        	questions.add(new Question("What is the square root of 16?",
                    new String[]{"a. 2", "b. 3", "c. 4", "d. 5"},
                    "c. 4"));
        	
        	questions.add(new Question("What is the volume of a cube of side 5?",
                    new String[]{"a. 25", "b. 50", "c. 75", "d. 125"},
                    "d. 125"));
        	
        	questions.add(new Question("What is the sum of the interior angles of a quadrilateral?",
                    new String[]{"a. 180", "b. 90", "c. 360", "d. 125"},
                    "c. 360"));
        	
        } else if (subject.equals("Maths") && difficulty.equals("Hard")) {
        	questions.add(new Question("What is the value of the derivative of x^2 at x = 3?",
                    new String[]{"a. 3", "b. 6", "c. 9", "d. 12"},
                    "b. 6","n*(x^(n-1))"));
        	
        	questions.add(new Question("What is the integral of 2x from 0 to 2?",
                    new String[]{"a. 2", "b. 4", "c. 6", "d. 8"},
                    "b. 4","Substitution of the given interval with the function and subtract highest with lowest one"));
        	
        	questions.add(new Question("What is the value of the factorial of 5 (5!)?",
                    new String[]{"a. 60", "b. 100", "c. 120", "d. 150"},
                    "c. 120","What is 1*2*3*4*5?"));
        	
        	questions.add(new Question("What is the sum of the interior angles of a hexagon?",
                    new String[]{"a. 360 degrees", "b. 540 degrees", "c. 720 degrees", "d. 900 degrees"},
                    "c. 720 degrees","Sum of interior angles=(n-2)*180,where n is the number of sides of the polygon"));
        	
        	questions.add(new Question("Solve for x in the equation 3x + 2 = 11",
                    new String[]{"a. 1", "b. 2", "c. 3", "d. 4"},
                    "c. 3","x=(11-2)/3"));
        
        	
        	questions.add(new Question("What is the value of Pi (π) to two decimal places?",
                    new String[]{"a. 3.12", "b. 3.14", "c. 3.15", "d. 3.16"},
                    "b. 3.14","This famous mathematical constant is approximately equal to 22/7 in fraction"));
        	
        	questions.add(new Question("If ABC is a right angled triangle, such that AB=3, BC=4, then what is AC?",
                    new String[]{"a. 7", "b. 12", "c. 1", "d. 5"},
                    "d. 5","Square root of the sum of the squares of the other two sides"));
        	
        	questions.add(new Question("What is 6372 + 5849?",
                    new String[]{"a. 14253", "b. 12221", "c. 06458", "d. 74839"},
                    "b. 12221","Add the numbers at the units place and the answer will have the same units place as the sum"));
        	
        } else if (subject.equals("Literature") && difficulty.equals("Easy")) {
        	questions.add(new Question("Who wrote 'Pride and Prejudice'?",
                    new String[]{"a. Emily Bronte", "b. Charlotte Bronte", "c. Jane Austen", "d. Mary Shelley"},
                    "c. Jane Austen"));
        	
        	questions.add(new Question("What is the name of the wizarding school in 'Harry Potter'?",
                    new String[]{"a. Beauxbatons", "b. Durmstrang", "c. Ilvermorny", "d. Hogwarts"},
                    "d. Hogwarts"));
        	
        	questions.add(new Question("Who wrote '1984'?",
                    new String[]{"a. Aldous Huxley", "b. George Orwell", "c. Ray Bradbury", "d. J.K. Rowling"},
                    "b. George Orwell"));
        	
        	questions.add(new Question("What is the first name of Sherlock Holmes’ assistant, Dr. Watson?",
                    new String[]{"a. John", "b. James", "c. Michael", "d. David"},
                    "a. John"));
        	
        	questions.add(new Question("Who wrote 'The Great Gatsby'?",
                    new String[]{"a. Ernest Hemingway", "b. F. Scott Fitzgerald", "c. William Faulkner", "d. John Steinbeck"},
                    "b. F. Scott Fitzgerald"));
        	
        	questions.add(new Question("In A Christmas Carol, how many ghosts visit Scrooge?",
                    new String[]{"a. Two", "b. Three", "c. Four", "d. Five"},
                    "c. Four"));
        	
        	questions.add(new Question("Who is Harry Potter’s best female friend?",
                    new String[]{"a. Cho Chang", "b. Luna Lovegood", "c. Ginny Weasley", "d. Hermione Granger"},
                    "d. Hermione Granger"));
        	
        	questions.add(new Question("Which author wrote about a boy named Charlie visiting a chocolate factory after winning a golden ticket?",
                    new String[]{"a. Roald Dahl", "b. J.K. Rowling", "c. Dr.Seuss", "d. C.S. Lewis"},
                    "a. Roald Dahl"));
        			
        } else if (subject.equals("Literature") && difficulty.equals("Medium")) {
        	questions.add(new Question("Who wrote 'To Kill a Mockingbird'?",
                    new String[]{"a. Ernest Hemingway", "b. Harper Lee", "c. J.D. Salinger", "d. F. Scott Fitzgerald"},
                    "b. Harper Lee"));
        	
        	questions.add(new Question("Who wrote the play 'Hamlet'?",
                    new String[]{"a. William Shakespeare", "b. Charles Dickens", "c. Jane Austen", "d. Leo Tolstoy"},
                    "a. William Shakespeare"));
        	
        	questions.add(new Question("What is the name of the epic poem written by Homer?",
                    new String[]{"a. The Iliad", "b. The Odyssey", "c. Beowulf", "d. The Divine Comedy"},
                    "a. The Iliad"));
        	
        	questions.add(new Question("Who wrote 'Moby Dick'?",
                    new String[]{"a. Nathaniel Hawthorne", "b. Herman Melville", "c. Mark Twain", "d. Walt Whitman"},
                    "b. Herman Melville"));
        	
        	questions.add(new Question("In 'To Kill a Mockingbird', what is the name of the narrator's father?",
                    new String[]{"a. Bob Ewell", "b. Atticus Finch", "c. Tom Robinson", "d. Arthur Radley"},
                    "b. Atticus Finch"));
        	
        	questions.add(new Question("Who wrote the poem 'The Raven'?",
                    new String[]{"a. Edgar Allan Poe", "b. Robert Frost", "c. Walt Whitman", "d. Emily Dickinson"},
                    "a. Edgar Allan Poe"));
        	
        	questions.add(new Question("What novel begins with the line, 'Call me Ishmael'?",
                    new String[]{"a. 'The Catcher in the Rye'", "b. 'The Great Gatsby'", "c. 'Moby Dick'", "d. '1984'"},
                    "c. 'Moby Dick'"));
        	
        	questions.add(new Question("Who wrote the line, “Where ignorance is bliss, it is folly to be wise”?",
                    new String[]{"a. William Shakespeare", "b. William Wordsworth", "c. Emily Dickson", "d. Oscar Wilde"},
                    "a. William Shakespeare"));
        	
        } else if (subject.equals("Literature") && difficulty.equals("Hard")) {
        	questions.add(new Question("Who wrote 'One Hundred Years of Solitude'?",
                    new String[]{"a. Gabriel Garcia Marquez", "b. Isabel Allende", "c. Jorge Luis Borges", "d. Mario Vargas Llosa"},
                    "a. Gabriel Garcia Marquez","A renowned Colombian writer known for his magical realism"));
        	
        	questions.add(new Question("Who is the author of 'Brave New World'?",
                    new String[]{"a. Aldous Huxley", "b. George Orwell", "c. H.G. Wells", "d. Ray Bradbury"},
                    "a. Aldous Huxley","Consider a famous English author known for dystopian literature"));
        	
        	questions.add(new Question("What is the title of the first novel in the 'A Song of Ice and Fire' series by George R.R. Martin?",
                    new String[]{"a. 'A Clash of Kings'", "b. 'A Storm of Swords'", "c. 'A Feast for Crows'", "d. 'A Game of Thrones'"},
                    "d. 'A Game of Thrones'","S medieval-themed struggle for power way back some centuries"));
        	
        	questions.add(new Question("Who wrote the play 'Waiting for Godot'?",
                    new String[]{"a. Samuel Beckett", "b. Harold Pinter", "c. Tom Stoppard", "d. Arthur Miller"},
                    "a. Samuel Beckett","A Nobel Prize-winning playwright known for his absurdist style"));
        	
        	questions.add(new Question("What is the name of the main character in 'The Catcher in the Rye'?",
                    new String[]{"a. Holden Caulfield", "b. Jay Gatsby", "c. Huckleberry Finn", "d. Tom Sawyer"},
                    "a. Holden Caulfield","A teenage protagonist struggling with alienation and identity"));
        	
        	questions.add(new Question("In what fictional town is Harper Lee’s To Kill A Mockingbird set?",
        		     new String[]{"a. Rivertown","b. Springfield","c. Maycomb","d. Green Hills"},
        		        "c. Maycomb","The first syllable is the name of a month"));
        	
        	questions.add(new Question("What do the initials J. D. stand for in author J. D. Salinger’s name?",
                     new String[]{"a. John Doe","b. Joseph Daniel","c. James Dean","d. Jerome David"},
        		        "d. Jerome David","The first name is short for Jeremy"));
        	
        	questions.add(new Question("Veronica Roth’s Divergent book series is set in a post-apocalyptic version of which U. S. city?",
        		    new String[]{"a. New York", "b. Chicago", "c. Los Angeles", "d. San Francisco"},
        		    "b. Chicago","The Willis Tower is situated in this city"));
        	
        	
        	
        } else if (subject.equals("Science") && difficulty.equals("Easy")) {
        	questions.add(new Question("What is the largest mammal in the world?",
                    new String[]{"a. African Elephant", "b. Blue Whale", "c. Giraffe", "d. Hippopotamus"},
                    "b. Blue Whale"));
        	
        	questions.add(new Question("What is the chemical symbol for water?",
                    new String[]{"a. H", "b. W", "c. O", "d. H2O"},
                    "d. H2O"));
        	
        	questions.add(new Question("What planet is known as the Earth's twin?",
                    new String[]{"a. Mars", "b. Venus", "c. Jupiter", "d. Saturn"},
                    "b. Venus"));
        	
        	questions.add(new Question("What is the boiling point of water at sea level?",
                    new String[]{"a. 90°C", "b. 95°C", "c. 100°C", "d. 110°C"},
                    "c. 100°C"));
        	
        	questions.add(new Question("What is the primary gas found in the Earth's atmosphere?",
                    new String[]{"a. Oxygen", "b. Nitrogen", "c. Carbon Dioxide", "d. Hydrogen"},
                    "b. Nitrogen"));
        	
        	questions.add(new Question("What is the process by which plants make their food?",
                    new String[]{"a. Respiration", "b. Photosynthesis", "c. Transpiration", "d. Germination"},
                    "b. Photosynthesis"));
        	
        	questions.add(new Question("What is the chemical symbol for sodium?",
                    new String[]{"a. Na", "b. So", "c. Sn", "d. Sd"},
                    "a. Na"));
        	questions.add(new Question("What part of the plant conducts photosynthesis?",
        		    new String[]{"a. Leaves", "b. Roots", "c. Stem", "d. Flowers"},
        		    "a. Leaves"));
        	
        } else if (subject.equals("Science") && difficulty.equals("Medium")) {
        	questions.add(new Question("What is the hardest known natural material?",
                    new String[]{"a. Quartz", "b. Diamond", "c. Graphite", "d. Corundum"},
                    "b. Diamond"));
        	
        	questions.add(new Question("Who discovered penicillin?",
                    new String[]{"a. Alexander Fleming", "b. Louis Pasteur", "c. Jonas Salk", "d. Joseph Lister"},
                    "a. Alexander Fleming"));
        	
        	questions.add(new Question("Who is known as the father of modern physics?",
                    new String[]{"a. Isaac Newton", "b. Albert Einstein", "c. Niels Bohr", "d. Galileo Galilei"},
                    "a. Isaac Newton"));
        	
        	questions.add(new Question("What is the powerhouse of the cell?",
                    new String[]{"a. Nucleus", "b. Ribosome", "c. Mitochondria", "d. Chloroplast"},
                    "c. Mitochondria"));
        	
        	questions.add(new Question("What is the chemical formula for table salt?",
                    new String[]{"a. NaCl", "b. KCl", "c. CaCl2", "d. MgCl2"},
                    "a. NaCl"));
        	
        	questions.add(new Question("Who proposed the theory of evolution by natural selection?",
                    new String[]{"a. Gregor Mendel", "b. Charles Darwin", "c. Jean-Baptiste Lamarck", "d. Alfred Wallace"},
                    "b. Charles Darwin"));
        	
        	questions.add(new Question("What is known as the 'Master Gland' of the human body?",
        		    new String[]{"a. Thyroid", "b. Pancreas", "c. Pituitary", "d. Adrenal"},
        		    "c. Pituitary"));
        	
        	questions.add(new Question("What is the only planet that spins clockwise?",
        		    new String[]{"a. Earth", "b. Mars", "c. Jupiter", "d. Venus"},
        		    "d. Venus"));

        	
        } else if (subject.equals("Science") && difficulty.equals("Hard")) {
        	questions.add(new Question("What is the second most abundant element in the Earth's crust?",
                    new String[]{"a. Silicon", "b. Aluminum", "c. Iron", "d. Calcium"},
                    "a. Silicon","Atomic number is 14"));
        	
        	questions.add(new Question("What particle in an atom has a negative charge?",
                    new String[]{"a. Proton", "b. Neutron", "c. Electron", "d. Positron"},
                    "c. Electron","This subatomic particle orbits the nucleus"));
        	
        	questions.add(new Question("What's the shape of the Earth?",
        		    new String[]{"a. Flat", "b. Round", "c. No shape", "d. Spheroid"},
        		    "d. Spheroid","It is formed by rotating an ellipse about one of its axes"));
        	
        	questions.add(new Question("What is the term for animals that are active during the night?",
                    new String[]{"a. Diurnal", "b. Crepuscular", "c. Nocturnal", "d. Cathemeral"},
                    "c. Nocturnal","It begins with the same first letter in night"));
        	
        	questions.add(new Question("What is the most abundant protein in the human body?",
                    new String[]{"a. Keratin", "b. Haemoglobin", "c. Collagen", "d. Myosin"},
                    "c. Collagen","This structural protein provides strength and support to tissues"));
        	
        	questions.add(new Question("What type of bond involves the sharing of electron pairs between atoms?",
                    new String[]{"a. Ionic", "b. Covalent", "c. Metallic", "d. Hydrogen"},
                    "b. Covalent","This bond results from the overlap of atomic orbitals"));
        	
        	questions.add(new Question("How many times does the Moon revolve around the Earth in a year?",
        		    new String[]{"a. 10", "b. 12", "c. 14", "d. 13"},
        		    "d. 13","Its considered to be an unlucky number"));
        	
        	questions.add(new Question("What is the equivalent megapixels of the human eye?",
        		    new String[]{"a. 576", "b. 300", "c. 108", "d. 432"},
        		    "a. 576","The square of 24"));
        	
        }    	  
    }

    public List<Question> getQuestions() {
        return questions;
    }
}