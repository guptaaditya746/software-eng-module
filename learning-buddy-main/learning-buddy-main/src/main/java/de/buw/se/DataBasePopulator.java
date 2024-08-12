package de.buw.se;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DataBasePopulator {

    public static void populateDatabase() {
        // Adding categories
        addCategoryFromFile("Chemistry", "src/main/resources/Chemistry.txt");
        addCategoryFromFile("History", "src/main/resources/History.txt");
        addCategoryFromFile("Mathematics", "src/main/resources/Mathematics.txt");
        addCategoryFromFile("Physics", "src/main/resources/Physics.txt");
        addCategoryFromFile("Biology", "src/main/resources/Biology.txt");

        // Adding questions
        int chemistry_category_id = 1;
        int history_category_id = 2;
        int mathematics_category_id = 3;
        int physics_category_id = 4;
        int biology_category_id = 5;
        String difficulty_beginner = "Beginner";
        String difficulty_advanced = "Advanced";
        
        // Chemistry questions
        String question_text = "What are elements?";
        String[] answers = {"Substances made up of only one type of molecule", 
                                    "Tiny particles that make up matter", 
                                    "Special kinds of atoms with unique properties", 
                                    "Liquids found in nature"};
        String right_answer = "Special kinds of atoms with unique properties";
        DataStoreSql.addQuestion(chemistry_category_id, difficulty_beginner, question_text, answers, right_answer);

        question_text = "What do atoms join together to form?";
        answers = new String[] {"Cells", "Molecules", "Planets", "Stars"};
        right_answer = "Molecules";
        DataStoreSql.addQuestion(chemistry_category_id, difficulty_beginner, question_text, answers, right_answer);

        question_text = "Why is understanding Chemistry important?";
        answers = new String[] {"To explore outer space", 
                                "To unlock the secrets of the world", 
                                "To learn about history", 
                                "To study living organisms"};
        right_answer = "To unlock the secrets of the world";
        DataStoreSql.addQuestion(chemistry_category_id, difficulty_beginner, question_text, answers, right_answer);

        question_text = "Which of the following is an example of a change in matter mentioned in the paragraph?";
        answers = new String[] {"Photosynthesis", 
                                "Oxidation", 
                                "Freezing of water", 
                                "Combustion"};
        right_answer = "Freezing of water";
        DataStoreSql.addQuestion(chemistry_category_id, difficulty_advanced, question_text, answers, right_answer);

        question_text = "What is the composition of water molecules?";
        answers = new String[] {"One hydrogen atom and one oxygen atom", 
                                "Two hydrogen atoms and one oxygen atom", 
                                "Two oxygen atoms and one hydrogen atom", 
                                "Two hydrogen atoms and two oxygen atoms"};
        right_answer = "Two hydrogen atoms and one oxygen atom";
        DataStoreSql.addQuestion(chemistry_category_id, difficulty_advanced, question_text, answers, right_answer);

        question_text = "Which process is an example of a chemical change?";
        answers = new String[] {"Water boiling into steam", 
                                "Melting of ice", 
                                "Burning of wood", 
                                "Cutting a piece of paper"};
        right_answer = "Burning of wood";
        DataStoreSql.addQuestion(chemistry_category_id, difficulty_advanced, question_text, answers, right_answer);

        // History questions
        question_text = "What is history?";
        answers = new String[] {"The study of the future", 
                                "The study of the past", 
                                "The study of the present", 
                                "The study of fiction"};
        right_answer = "The study of the past";
        DataStoreSql.addQuestion(history_category_id, difficulty_beginner, question_text, answers, right_answer);

        question_text = "Which ancient civilization built the pyramids?";
        answers = new String[] {"Ancient Greece", 
                                "Ancient Rome", 
                                "Ancient Egypt", 
                                "Ancient China"};
        right_answer = "Ancient Egypt";
        DataStoreSql.addQuestion(history_category_id, difficulty_beginner, question_text, answers, right_answer);
        
        question_text = "What was the main economic system in the Middle Ages?";
        answers = new String[] {"Capitalism", 
                                "Socialism", 
                                "Feudalism", 
                                "Mercantilism"};
        right_answer = "Feudalism";
        DataStoreSql.addQuestion(history_category_id, difficulty_beginner, question_text, answers, right_answer);

        question_text = "What were some achievements of ancient civilizations like Mesopotamia and Egypt?";
        answers = new String[] {"Advancements in agriculture and irrigation", 
                                "Invention of the wheel", 
                                "Development of writing systems", 
                                "All of the above"};
        right_answer = "All of the above";
        DataStoreSql.addQuestion(history_category_id, difficulty_advanced, question_text, answers, right_answer);

        question_text = "During the Middle Ages, what was the role of knights in feudal society?";
        answers = new String[] {"They were merchants who traded goods across Europe", 
                                "They were skilled craftsmen who built castles and cathedrals", 
                                "They were warriors who served lords in exchange for land", 
                                "They were scholars who preserved ancient texts and knowledge"};
        right_answer = "They were warriors who served lords in exchange for land";
        DataStoreSql.addQuestion(history_category_id, difficulty_advanced, question_text, answers, right_answer);

        question_text = "How did the Industrial Revolution change society in the 18th and 19th centuries??";
        answers = new String[] {"It led to the decline of cities and the rise of rural communities", 
                                "It increased the demand for skilled labor and improved living standards", 
                                "It resulted in the spread of feudalism and the weakening of monarchies", 
                                "It caused a decrease in technological innovation and scientific progress"};
        right_answer = "It increased the demand for skilled labor and improved living standards";
        DataStoreSql.addQuestion(history_category_id, difficulty_advanced, question_text, answers, right_answer);

        // Mathematics questions
        question_text = "What is the result of 2 + 2?";
        answers = new String[] {"3", "4", "5", "6"};
        right_answer = "4";
        DataStoreSql.addQuestion(mathematics_category_id, difficulty_beginner, question_text, answers, right_answer);

        question_text = "What is a variable in algebra?";
        answers = new String[] {"A constant number", "A letter representing a number", "A shape", "An operation"};
        right_answer = "A letter representing a number";
        DataStoreSql.addQuestion(mathematics_category_id, difficulty_beginner, question_text, answers, right_answer);

        question_text = "Which shape has three sides?";
        answers = new String[] {"Circle", "Square", "Triangle", "Pentagon"};
        right_answer = "Triangle";
        DataStoreSql.addQuestion(mathematics_category_id, difficulty_beginner, question_text, answers, right_answer);

        question_text = "What is the derivative of x^2?";
        answers = new String[] {"2x", "x", "2x^2", "x^2 + 1"};
        right_answer = "2x";
        DataStoreSql.addQuestion(mathematics_category_id, difficulty_advanced, question_text, answers, right_answer);

        question_text = "What is the integral of 2x?";
        answers = new String[] {"x^2 + C", "2x^2", "x + C", "x^2"};
        right_answer = "x^2 + C";
        DataStoreSql.addQuestion(mathematics_category_id, difficulty_advanced, question_text, answers, right_answer);

        question_text = "What is the formula for the area of a circle?";
        answers = new String[] {"πr^2", "2πr", "πd", "r^2"};
        right_answer = "πr^2";
        DataStoreSql.addQuestion(mathematics_category_id, difficulty_advanced, question_text, answers, right_answer);

        // Physics questions
        question_text = "What is the study of matter and energy?";
        answers = new String[] {"Chemistry", "Biology", "Physics", "Mathematics"};
        right_answer = "Physics";
        DataStoreSql.addQuestion(physics_category_id, difficulty_beginner, question_text, answers, right_answer);

        question_text = "Which branch of physics deals with motion and forces?";
        answers = new String[] {"Mechanics", "Optics", "Thermodynamics", "Electromagnetism"};
        right_answer = "Mechanics";
        DataStoreSql.addQuestion(physics_category_id, difficulty_beginner, question_text, answers, right_answer);

        question_text = "What is the basic unit of electric current?";
        answers = new String[] {"Volt", "Watt", "Ampere", "Ohm"};
        right_answer = "Ampere";
        DataStoreSql.addQuestion(physics_category_id, difficulty_beginner, question_text, answers, right_answer);

        question_text = "What is the law of conservation of energy?";
        answers = new String[] {"Energy can be created or destroyed", "Energy is always lost in a system", "Energy cannot be created or destroyed", "Energy is constant in all reactions"};
        right_answer = "Energy cannot be created or destroyed";
        DataStoreSql.addQuestion(physics_category_id, difficulty_advanced, question_text, answers, right_answer);

        question_text = "What is the speed of light in a vacuum?";
        answers = new String[] {"3 x 10^8 m/s", "3 x 10^6 m/s", "3 x 10^7 m/s", "3 x 10^9 m/s"};
        right_answer = "3 x 10^8 m/s";
        DataStoreSql.addQuestion(physics_category_id, difficulty_advanced, question_text, answers, right_answer);

        question_text = "What is the principle of superposition in quantum mechanics?";
        answers = new String[] {"Particles can exist in multiple states simultaneously", "Energy levels are quantized", "Electrons orbit the nucleus", "Particles have definite positions and momenta"};
        right_answer = "Particles can exist in multiple states simultaneously";
        DataStoreSql.addQuestion(physics_category_id, difficulty_advanced, question_text, answers, right_answer);

        // Biology questions
        question_text = "What is the basic unit of life?";
        answers = new String[] {"Atom", "Molecule", "Cell", "Organ"};
        right_answer = "Cell";
        DataStoreSql.addQuestion(biology_category_id, difficulty_beginner, question_text, answers, right_answer);

        question_text = "Which molecule carries genetic information?";
        answers = new String[] {"Protein", "Carbohydrate", "DNA", "Lipid"};
        right_answer = "DNA";
        DataStoreSql.addQuestion(biology_category_id, difficulty_beginner, question_text, answers, right_answer);

        question_text = "What is the study of ecosystems?";
        answers = new String[] {"Genetics", "Anatomy", "Ecology", "Evolution"};
        right_answer = "Ecology";
        DataStoreSql.addQuestion(biology_category_id, difficulty_beginner, question_text, answers, right_answer);

        question_text = "What process do plants use to convert sunlight into energy?";
        answers = new String[] {"Respiration", "Photosynthesis", "Fermentation", "Digestion"};
        right_answer = "Photosynthesis";
        DataStoreSql.addQuestion(biology_category_id, difficulty_advanced, question_text, answers, right_answer);

        question_text = "What is the function of ribosomes in a cell?";
        answers = new String[] {"Energy production", "Protein synthesis", "DNA replication", "Cell division"};
        right_answer = "Protein synthesis";
        DataStoreSql.addQuestion(biology_category_id, difficulty_advanced, question_text, answers, right_answer);

        question_text = "What is natural selection?";
        answers = new String[] {"A method of genetic engineering", "The process by which organisms better adapted to their environment tend to survive and produce more offspring", "A theory of how life began on Earth", "A type of asexual reproduction"};
        right_answer = "The process by which organisms better adapted to their environment tend to survive and produce more offspring";
        DataStoreSql.addQuestion(biology_category_id, difficulty_advanced, question_text, answers, right_answer);
    }

    public static void addCategoryFromFile(String category_name, String file_path) {
        try {
            String category_study_information = new String(Files.readAllBytes(Paths.get(file_path)));
            DataStoreSql.addCategory(category_name, category_study_information);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}