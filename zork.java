package com.company;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import static com.company.zork.so;

/*====================================================================================================================/

CLASS: CPSC 223J-01
GROUP MEMBERS: Jason Lee, Christopher Briseno, Dereck Jimenez, Aren Laure Lizardo

PROJECT: ZORK GAME CLONE: THE LEGEND OF THE DARKEST TOWER

PROJECT DESCRIPTION:
A clone that follows the same game design as the famous text-adventure game where a player must
input word commands to traverse through the world that is 'Zork.' For this project, we deviate from the original game
and made our own text-adventure called 'The Legend of the Darkest Tower.' This game features puzzles, keys, food, and
monsters. Players must go up through the levels to reach the top and win the game before the monsters eat your soul.

NOTES:
Traversing through rooms uses an array list as a travel log where direction returns an index of the next room.
Inventory is just like Room ArrayList.
There is no scoring system. Just win the game before dropping your health to 0.
This code supposedly works on Java 14 and above. Java 8 and it breaks apart. Don't know why.

/====================================================================================================================*/

public class zork {
    static PrintStream so = System.out;
    public static void main(String[] args) {
        int playerHealth = 100;                                                     //initialize global player health
        int currentRoom = 1;                                                        //index of rooms; starts at 1 because 0 is a default room
        int hasInv = 0;                                                             //flag to check if you have something or not
        int light = 0;                                                              //simple light flag
        int flag = 0;                                                               //acts as the break for game
        Scanner entry = new Scanner(System.in);                                     //Scanner class to get user input
        Item noItem = new Item("noItem", "There seems to be nothing here...", "empty");     //empty item class
        noItem.setCount(-1);                                                                                    //count = -1 as a flag for "no item" for some cases
        Monster noMonster = new Monster("noMonster", "There's nobody here...", "empty");    //empty monster class
        ArrayList<Item>inv = new ArrayList<>();                                     //ArrayList inventory where items are stored with index as their ID
        ArrayList<Room>map = new ArrayList<>();                                     //ArrayList map where Rooms are stored with index as their ID

        //=============THE MONSTERS=============//
        Monster skeleton = new Monster("skeleton", "A bony reminiscent of a lifetime's " +
                "past, animated to that of a bare bone-like creature. ", "enemy", 15, 5);
        Monster skeleton2 = new Monster("skeleton", "A bony reminiscent of a lifetime's " +
                "past, animated to that of a bare bone-like creature. ", "enemy", 15, 5);
        Monster grue = new Monster("grue", "It's a grue! ...What the Zork is a grue?",
                "enemy", 25, 5);
        Monster goblin = new Monster("goblin", "A vile, green-skinned parasite that has warts and dirt "+
                "covering every inch of its filthy frame. ", "enemy", 45, 10);
        Monster demon = new Monster("demon", "A demon that reeks in evil and darkness, waiting for you "+
                "to succumb to its devilish needs. The sight of it leaves you numb.", "enemy", 45, 15);
        Monster dragon = new Monster("dragon", "It's a large bodied reptile with bat-like wings equipped " +
                "with sharp talons and a huge barbed tail", "enemy", 60, 20);

        //=============ITEMS=============//
        Item bronzeKey = new Item("bronze key", "It's a bronze key. Still shiny after so many years. Must " +
                "have a use here.", "key");
        Item jeweledSkull = new Item("jeweled skull", "A jewel-encrusted skull with a bit of weight " +
                "to it. It's too shiny to let go. The top of its head looks like it can open. ", "key");
        Item goldKey = new Item("golden key", "A gold key. Looks like it belongs to a " +
                "golden keyhole.", "key");
        Item skeletonKey = new Item("skeleton key", "A key with a head shaped like a skull. " +
                "Very cool.", "key");
        Item redGem = new Item("red gem", "A shiny red gem that sparkles blindly in any hint of light. " +
                "Might be worth carrying.", "key");
        Item blueGem = new Item("blue gem", "A blue gem that's close to the color of the ocean. Its " +
                "sparkle lulls you close to a calmness.", "key");
        Item greenGem = new Item("green gem", "A green gem where an emerald hue is shown around it. " +
                "Sparkly.", "key");
        Item bomb = new Item("bomb", "A really big bomb. Like, comically huge. Imagine big, " +
                "but bigger! Can destroy a cracked wall.", "bomb");

        //=============WEAPONS=============//
        Weapon sword = new Weapon("rusty sword", "A heavy sword that's hard to swing, but feels like " +
                "it could do some damage!", "weapon", 5);
        Weapon ironSword = new Weapon("iron sword", "A sharp and shiny that can hack and slash anything " +
                "in its path!","weapon", 15);
        Weapon grenade = new Weapon("grenade", "An ancient looking relic that has a pull-able pin to " +
                "it. It's been scribed with '1943 MK2 Grenade.' Attack to throw it. One use.", "weapon", 50);

        Food food = new Food("food", "Moldy food that's been left to rot for ages... it's " +
                "surprisingly still good to eat!", "consumable");

                                //Item indexes
        inv.add(bronzeKey);     //0
        inv.add(jeweledSkull);  //1
        inv.add(goldKey);       //2
        inv.add(skeletonKey);   //3
        inv.add(redGem);        //4
        inv.add(blueGem);       //5
        inv.add(bomb);          //6
        inv.add(sword);         //7
        inv.add(ironSword);     //8
        inv.add(grenade);       //9
        inv.add(food);          //10
        inv.add(greenGem);      //11

        //Start adding rooms : (int) = index : L = Level, R = Room
        map.add(new Room("Default Room", -1,-1,25,-1));        //Default Room
        map.add(new Room(r01, -1,4,-1,2, bronzeKey));                   // 1: L1, R1
        map.add(new Room(r02, -1,3,1,-1, sword));                       // 2: L1, R2
        map.add(new Room(r03, 2,-1,4,-1, food));                        // 3: L1, R3
        map.add(new Room(r04, 1,-1,5,3));                               // 4: L1, R4
        map.add(new Room(r05, -1,-1,-1,4));                             // 5: L1, R5
        map.add(new Room(r06, 7,5,-1,8));                               // 6: L2, R1
        map.add(new Room(r07, -1,6,-1,8, jeweledSkull));                // 7: L2, R2
        map.add(new Room(r08, -1,6,7,9, food));                         // 8: L2, R3
        map.add(new Room(r09, -1,10,8,-1, ironSword));                  // 9: L2, R4  <---- //weapon and enemy at choke; makes sure the player gets a weapon
        map.add(new Room(r10, 9,-1,-1,-1));                             //10: L2, R5        //no matter what, so it doesn't soft-lock
        map.add(new Room(r11, -1,10,-1,12, food));                      //11: L3, R1
        map.add(new Room(r12, -1,13,11,14, goldKey));                   //12: L3, R2
        map.add(new Room(r13, 12,-1,-1,14, bomb));                      //13: L3, R3
        map.add(new Room(r14, 12,13,-1,-1));                            //14: L3, R4
        map.add(new Room(r15, -1,-1,14,-1));                            //15: L3, R5
        map.add(new Room(r16, 17,19,15,-1, grenade));                   //16: L4, R1
        map.add(new Room(r17, 18,19,16,20, skeletonKey));               //17: L4, R2
        map.add(new Room(r18, 17,20,-1,-1, food));                      //18: L4, R3
        map.add(new Room(r19, 16,20,-1,17, food));                      //19: L4, R4
        map.add(new Room(r20, 18,19,17,-1));                            //20: L4, R5
        map.add(new Room(r21, 22,24,20,-1));                            //21: L5, R1
        map.add(new Room(r22, -1,-1,21,23, food));                      //22: L5, R2
        map.add(new Room(r23, 22,24,-1,-1, redGem));                    //23: L5, R3
        map.add(new Room(r24, -1,-1,21,23, blueGem));                   //24: L5, R4
        map.add(new Room(r25, 24,-1,-1,26, food));                      //25: L5, R5
        map.add(new Room(r26, -1,-1,25,-1));                            //26: L5, R6
        map.add(new Room(r27, -1,-1,26,-1));                            //27: L5, R7
        map.get(0).setItem(noItem);

        //Add the monsters
        map.get(2).addMonster(skeleton);
        map.get(9).addMonster(skeleton2);
        map.get(13).addMonster(grue);
        map.get(17).addMonster(goblin);
        map.get(25).addMonster(demon);
        map.get(26).addMonster(dragon);

        so.println(intro);
        while (flag != 1) {
            so.println("What is your command?");
            String word = entry.nextLine();
            word = word.toLowerCase();
            if (word.equals("surrender") || word.equals("succumb")) {
                so.println("You have surrendered to THE DARK TOWER.");
                flag = 1;
            }
            Command command = new Command(word);
            if (command.getCommand().equals("help")) {
                so.println(help);
            }
            if (command.getCommand().equals("light") && command.getSecondWord().equals("torch") && light == 0) {    //light command
                light = 1;
                so.println("Let there be light.");
                so.println(map.get(currentRoom).description());
            }
            if (light == 0 && !(word.equals("quit") || word.equals("exit"))) {              //you cannot do anything unless you light torch
                so.println("It's too dark to do anything.");
            }
            if (light == 1) {                                                               //continue
                if (command.processCommand(command, inv) == map.get(currentRoom).item()
                        && map.get(currentRoom).item() != null) {
                    so.println("Taking " + map.get(currentRoom).item().name() + ".");
                    map.get(currentRoom).add();
                    map.get(currentRoom).setItem(null);
                    hasInv++;
                } else if (command.useCommand(map, inv) == map.get(currentRoom)) {                  //use command
                    if (inv.get(command.getItemIndex()).use()) {                                    //checks first if you can use
                        so.println("You used the " + inv.get(command.getItemIndex()).name() + "."); //send confirmation to player
                        inv.get(command.getItemIndex()).destroy();                                  //destroy it (count = 0), item did its job
                        hasInv--;                                                                   //minus inventory
                    }
                } else if (command.useCommand(map, inv) != map.get(currentRoom) && command.getCommand().equals("use")) {    //else, can't use
                    so.println("You can't use this here.");
                } else if (command.eatCommand(inv) == food && inv.get(10).getCount() >= 1) {    //eat command
                    food.consume();                                                             //food--
                    playerHealth = playerHealth + food.restoreHP();                             //add to playerHealth
                    if (playerHealth > 100) {    //set maximum health
                        playerHealth = 100;
                    }
                    so.println("Your health is at " + playerHealth);
                    hasInv--;
                } else if (command.eatCommand(inv) == food && inv.get(10).getCount() == 0) {
                    so.println("There's nothing to eat.");
                } else if (command.examineCommand(inv) == inv.get(command.getItemIndex())) {    //examine command
                    if (inv.get(command.getItemIndex()).getCount() >= 1) {                      //checks if count >= 1 for both item and food
                        so.println(inv.get(command.getItemIndex()).description());
                    } else {
                        so.println("There's nothing to examine.");
                    }
                } else if (map.get(currentRoom).monster() != null && command.getCommand().equals("attack")) {   //attack command, check if room has monster or not
                    if (map.get(currentRoom).monster().tag().equals("enemy")) {                                 //if so, check tag
                        playerHealth = playerHealth - command.attackCommand(map, currentRoom, inv);             //attackCommand returns monster's damage
                        so.println("Your health is at " + playerHealth);
                    } else if (map.get(currentRoom).monster().tag().equals("dead")) {                           //if dead, tell player
                        so.println("It's already dead. No need to attack.");
                    }
                } else if (map.get(currentRoom).monster() == null && command.getCommand().equals("attack")) {   //check for null monster
                    so.println("Attack what? There's nobody here...");
                } else {
                    command.lookCommand(map, currentRoom);                                                      //look command, returns special strings for specific rooms
                }

                if (command.getCommand().equals("open") && command.getSecondWord().equals("jeweled skull") && inv.get(1).getCount() == 1) { //SUPER special line to make an "open" command
                    if (map.get(currentRoom).item() == null) {
                        so.println("The skull opens and plops out a green gem onto the floor. Might be useful to take.");
                        map.get(currentRoom).setItem(greenGem);                     //sets the room's item so take command can be used
                    } else if (map.get(currentRoom).item() != null) {               //hence the !null item check
                        so.println("No space to open it. Try a different room.");
                    }
                } else if (command.getCommand().equals("open")) {                   //if they try to open anything else, send an error
                    so.println("Can't open that.");
                }

                if (command.getCommand().equals("inventory")) {                     //inventory command
                    so.println("This is what's in your bag so far...");             //if count = 1, print that item
                    for (Item i : inv) {
                        if (i.getCount() >= 1) {
                            so.println(i.name());
                        }
                    }
                    if (hasInv == 0) {                                              //if they have nothing
                        so.println("Nothing.");
                    }
                }

                if (command.hasDirection()) {
                    if (map.get(currentRoom).direction(command.getDirection()) == -1) {
                        so.println("You cannot go here...");
                    } else if (map.get(currentRoom).monster() != null) {
                        if (map.get(currentRoom).monster().tag().equals("enemy")) {                 //In the case there's a monster, the player cannot go through
                            so.println("You can't go through! There's a monster!");
                        } else {
                            currentRoom = map.get(currentRoom).direction(command.getDirection());   //Otherwise, if the monster is dead, go through
                            so.println(map.get(currentRoom).description());
//                            so.printf("You're at index room: %d\n", currentRoom);                   //Remove this line at submission
                        }
                    } else {
                        currentRoom = map.get(currentRoom).direction(command.getDirection());       //Else when there's no monster, update currentRoom
                        so.println(map.get(currentRoom).description());
//                        so.printf("You're at index room: %d\n", currentRoom);                       //Remove this line at submission
                    }
                }
            }
//            if (word.equals("test")) {                                                              //Remove this command at submission
//                so.println("Current room: " + currentRoom);
//            }
            if (playerHealth <= 0) {            //Game over.
                so.println("Game Over.");
                flag = 1;
            }
            if (currentRoom == 27) {            //Game win.
                so.println("You take a look at the world around you and see the sun setting, hear birds chirping, and can smell the warm and earthy air." +
                        "\nYou've successfully beaten THE DARKEST TOWER.");
                flag = 1;
            }
        }
    }
    static String intro = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nWELCOME TO THE LEGEND OF THE DARKEST TOWER! " +
            "\nYou are exiled and dropped into an abandoned prison tower, north of Feurough. Facing certain death, " +
            "\nyou are left to survive in a dark and twisted tower that is filled with whatever lurks in the shadows. " +
            "\nDo whatever you can to get through the rooms and advance up the levels " +
            "\nand reach the top where there is salvation. Use your wits and find anything to get through it all..." +
            "\nWhen your back is against the wall and all hope is lost, type help.\n" +
            "=======================================================================================================" +
            "\nYou wake from unconsciousness and feel dizzy as you stand up." +
            "\nYou're in a dark room. Your hand is holding onto an unlit torch.";
    static String help =
                    "SALVATION: Reach the highest level of the game to escape the prison.\n" +               //IMMERSIVE DETAILS
                    "The game works almost like Zork.\n" +
                    "Input these commands whenever useful, otherwise the game will tell you.\n" +
                    "In any case you made a command and nothing is returned, probably not the solution.\n" +
                    "Try another command.\n" +
                    "COMMANDS:\n" +
                    "HELP: help\n" +
                            "\tDisplays instructions on how to play the game.\n" +
                    "LIGHT: light torch\n" +
                            "\tLight the torch.\n" +
                    "LOOK: look\n" +
                            "\tTells you what you need to know in the room and enemies afoot.\n" +
                    "GO: go [direction]\n" +
                            "\tGo any direction if possible. Directions are north(n), south(s), east(e) and west(w).\n" +
                    "TAKE: take [item]\n" +
                            "\tIf there's an item in the room, you can pick it up.\n" +
                    "USE: use [item]\n" +
                            "\tUse the item when necessary for puzzles.\n" +
                    "OPEN: open [item]\n" +
                            "\tOpen a special item when necessary.\n" +
                    "CHECK INVENTORY: inventory\n" +
                            "\tShows your inventory.\n" +
                    "EXAMINE: examine [item]\n" +
                            "\tExamines the item in your inventory, if you have any.\n" +
                    "EAT: eat [food]\n" +
                            "\tIf you have food, eat it to get some health back.\n" +
                    "ATTACK: attack [monster] with [weapon] *OR* attack with [weapon]\n" +
                            "\tEverytime you attack, you will receive damage from the monster. Be careful.\n" +
                            "\tAttacking with only weapon and you will attack the monster in the room nonetheless.\n" +
                    "QUIT: quit *OR* exit\n" +
                    "Press [ENTER] to input your command.\n" +
                    "Capitalization doesn't matter. And please, don't put a space after your command.";
    static String r01 = "The torch is lit and all you see is two wooden doors. One door has a broken lock and leads to " +
            "\nthe south while the other has missing hinges and leads to the east.";
    static String r02 = "You've stumbled into another room filled with dust and bugs. All you can see are two doors in " +
            "\nthe distance. One door, covered in moss, goes west while the other door, covered in webs goes south.";
    static String r03 = "You have now entered another dark and grimey room. You cannot help but notice a door to the \n" +
            "north and another door to the west. The door to the north has blood stains all over while the one to the west seems untouched.";
    static String r04 = "You enter a room and can faintly make out the appearance of three doors. \n" +
            "One to the east, one to the west and one to the north of the room";
    static String r05 = "You come up to another room. The walls are plain and smooth. " +
            "\nThere's a bronze gate to the south. There's also a door to the east.";
    static String r06 = "Going through the bronze gate leads you up a flight of stairs to another floor. " +
            "\nStill enshrouded in darkness due to the lowlight of the torch, you can only see " +
            "\ndoors on the north and east side of the room. The staircase at the south leads back to the lower level.";
    static String r07 = "You enter the room and see a dimly lit area. You can make out the doors at the south and east side of the room.";
    static String r08 = "You enter this room and take a wide look around. Now there are three distinct doors. " +
            "\nThere is one west which is a faint blue color, one south that has words in big letters that say DO NOT ENTER, " +
            "\nand the one east with a red light coming from the bottom.";
    static String r09 = "This room has a funky smell to it. There are also two doors, one to the west and one to the south.";
    static String r10 = "You see two gates, both of which look as though they're made of bones. " +
            "\nYou approach the west gate and realize that the other side had collapsed and is a dead end. " +
            "\nThe east gate seems to be the only passage.";
    static String r11 = "Going through the skeleton gate leads you up another flight of stairs onto another floor. " +
            "\nAll you can see is a door on the south side and east side.";
    static String r12 = "The room's north side is filled with empty cells. Ragged clothing is scattered throughout the floor and not a soul is found." +
            "\nThere are doors on the west, south and east side of the room.";
    static String r13 = "This room looks very similar to the previous room where empty cells fill the west and south side. " +
            "\nHay from the makeshift prison beds scatter across the stone-cold floor." +
            "\nDoor at west, south, and east.";
    static String r14 = "A giant cell sits at the west of the room. It's too far deep to tell what's inside, " +
            "\nbut you hear a distant growl from darkness. You dare not to get closer. " +
            "\nThe passageways at north and south lead you back to the cell rooms while the east leads you to a hallway.";
    static String r15 = "It's a short hallway corner where at the south is a golden door. West takes you back to the cell rooms.";
    static String r16 = "The warden's quarters. There's no one hear and the room seems to be long abandoned. " +
            "\nThere's a door at the north and at the south.";
    static String r17 = "It's a seemingly normal room where the ceiling depicts a faded painting of what seems to be " +
            "\na starry sky filled with storm clouds and clashing lightning strikes. " +
            "\nThis room has passageways all-around.";
    static String r18 = "A seemingly long hallway where the walls are filled with ripped paintings and tattered tapestry. " +
            "\nCabinets by the walls have wood-rot and appear to have nothing inside. " +
            "\nThe hallway has a clearing at north and south.";
    static String r19 = "This seems to be the barracks. Bunk beds by rows lay firm with dirty blankets " +
            "\nand stained pillowcases while the weaponry is ransacked and bare empty. Not much use from here. " +
            "\nThe passageway at north leads back to the starting room. There is a passage at east and south.";
    static String r20 = "The room smells like rotten meat. The walls are chiseled and have the color of charcoal. " +
            "\nBy the east sits a menacing skeleton door. There are passageways at west, north, and south.";
    static String r21 = "It's a plain room with cobblestone all-around, wet from the moisture as the sounds of screaming is heard from the south. " +
            "\nWest leads back to a staircase from the skeleton door. At the north there's a passageway and at the south is an open door.";
    static String r22 = "Chains and cages hung high from the ceiling, filled with bones and fleshy bits. " +
            "\nThere are passageways at west and east.";
    static String r23 = "The walls are wet from moisture and heat rises through the cracks in the floor. " +
            "\nThere's a passageway at north and south.";
    static String r24 = "Walls around are covered in stained blood and chipped scratches from other... beings. " +
            "\nThe entrance at the west goes back to the first room. There's another broken doorway at the east.";
    static String r25 = "The room is covered in a dark red hue. It feels hopeless in here. " +
            "\nThe passageway at the north goes back. There seems to be no other path... or so it seems.";
    static String r26 = "The walls are covered high in gold coins and treasure while the room reeks in reptile musk and hot steam mixed unpleasantly. " +
            "\nThe hallway at west leads back.";
    static String r27 = "You've finally reached the top of the tower.";
}

class Command{
    private int direction = -999;
    private int itemIndex;
    Item item;
    private String commandWord;
    private String secondWord;
    private String weaponWord;
    public Command(String firstWord) {
        commandWord = firstWord;
        secondWord = "";                                                    //if this was null, there would 100+ errors, so leave it as is.
        if (firstWord.contains(" ")) {                                      //if there's a space, convert into two words, or phrase
            commandWord = firstWord.substring(0, firstWord.indexOf(" "));
            secondWord = firstWord.substring(firstWord.indexOf(" ") + 1);
        }
    }

    public String getCommand() { return commandWord; }                      //getters
    public String getSecondWord() { return secondWord; }

    public boolean hasDirection() { return direction >= 0; }

    public boolean checkCommand() {                                         //verb check, else tell player wrong input
        String[] verb = {   "go", "look", "use",
                            "attack", "examine", "take",
                            "inventory", "help", "quit",
                            "eat", "light", "exit", "open"};
        for (String s : verb) {
            if (commandWord.equals(s)) {
                return true;
            }
        }
        return false;
    }

    public Item processCommand(Command command, ArrayList<Item>inv) {
        if (!command.checkCommand()) {                                      //check the command if it's a verb or not
            so.println("What are you talking about?");
        } else if (commandWord.equals("go")) {                              //move command
            if (secondWord.equals("")) {
                so.println("Go where?");
            } else {
                switch (secondWord) {                                       //change direction into an integer
                    case "north", "n" -> direction = 0;
                    case "south", "s" -> direction = 1;
                    case "west", "w" -> direction = 2;
                    case "east", "e" -> direction = 3;
                }
            }
        } else if (commandWord.equals("take")) {                            //take command
            if (secondWord.equals("")) {
                so.println("Take what? I don't understand.");
            } else {
                switch (secondWord) {
                    case "bronze key": return inv.get(0);                   //switch case with inv item return
                    case "jeweled skull": return inv.get(1);
                    case "golden key": return inv.get(2);
                    case "skeleton key": return inv.get(3);
                    case "red gem": return inv.get(4);
                    case "blue gem": return inv.get(5);
                    case "bomb": return inv.get(6);
                    case "rusty sword": return inv.get(7);
                    case "iron sword": return inv.get(8);
                    case "grenade": return inv.get(9);
                    case "food": return inv.get(10);
                    case "green gem": return inv.get(11);
                }
                so.println("Huh? Take what, again?");
            }
        }
          return item;
    }

    public Item eatCommand(ArrayList<Item>inv) {                //simple eat command, only checks if second word is food
        if (commandWord.equals("eat")) {
            if (secondWord.equals("")) {
                so.println("Eat what? I don't understand.");
            } else if (secondWord.equals("food")) {
                return inv.get(10);
            } else {
                so.println("You can't eat that.");
            }
        }
        return item;
    }

    public Item examineCommand(ArrayList<Item>inv) {
        if (commandWord.equals("examine")) {
            if (secondWord.equals("")) {
                so.println("There's nothing to examine.");
            } else {
                switch (secondWord) {
                    case "bronze key": itemIndex = 0; return inv.get(itemIndex);            //itemIndex needed for inv.get(i) outside class
                    case "jeweled skull": itemIndex = 1; return inv.get(itemIndex);
                    case "golden key": itemIndex = 2; return inv.get(itemIndex);
                    case "skeleton key": itemIndex = 3; return inv.get(itemIndex);
                    case "red gem": itemIndex = 4; return inv.get(itemIndex);
                    case "blue gem": itemIndex = 5; return inv.get(itemIndex);
                    case "bomb": itemIndex = 6; return inv.get(itemIndex);
                    case "rusty sword": itemIndex = 7; return inv.get(itemIndex);
                    case "iron sword": itemIndex = 8; return inv.get(itemIndex);
                    case "grenade": itemIndex = 9; return inv.get(itemIndex);
                    case "food": itemIndex = 10; return inv.get(itemIndex);
                    case "green gem": itemIndex = 11; return inv.get(itemIndex);
                }
                so.println("Hm? What do you mean to examine?");
            }
        }
        return item;
    }

    public Room useCommand(ArrayList<Room>map, ArrayList<Item>inv) {
        if (commandWord.equals("use")) {
            if (secondWord.equals("")) {
                so.println("Nowhere to use this.");
            } else {
                switch (secondWord) {
                    case "bronze key":                                    //puzzle rooms
                        itemIndex = 0;
                        if (inv.get(0).use()) {
                            map.get(5).update(1, 6);
                            so.println("The bronze gate creaks open, the rust falling off its hinges...");
                            return map.get(5);
                        }
                    case "jeweled skull":
                        itemIndex = 1;
                        if (inv.get(itemIndex).use()) {
                            map.get(10).update(3, 11);
                            so.println("The skeleton gates collapses and turns to dust, as if by magic! \nA hallway at the east leads upwards.");
                            return map.get(10);
                        }
                    case "golden key":
                        itemIndex = 2;
                        if (inv.get(itemIndex).use()) {
                            map.get(15).update(1, 16);
                            so.println("The golden door shines and opens gracefully, revealing another set of stairs south.");
                            return map.get(15);
                        }
                    case "skeleton key":
                        itemIndex = 3;
                        if (inv.get(itemIndex).use()) {
                            map.get(20).update(3, 21);
                            so.println("The skeleton door opens like a chatterbox filled with bones. It stops just enough for you to go through.");
                            return map.get(20);
                        }
                    case "red gem":
                        itemIndex = 4;
                        if (inv.get(itemIndex).use()) {
                            map.get(25).update(3, 26);
                            so.println("The pressure plate clicks in satisfaction, the walls opening to another pathway at the east.");
                            return map.get(25);
                        }
                    case "blue gem":
                        itemIndex = 5;
                        if (inv.get(itemIndex).use()) {
                            map.get(26).update(0, 27);
                            so.println("The pressure plate clicks in satisfaction as a blinding lights cracks through an opening in the wall at the north. " +
                                    "\nYou are thin enough to go through.");
                            return map.get(26);
                        }
                    case "green gem":
                        itemIndex = 11;
                        if (inv.get(itemIndex).use()) {
                            map.get(24).update(1, 25);
                            so.println("The pressure plate clicks in satisfaction. The door at the south opens with a clunk.");
                            return map.get(24);
                        }
                    case "bomb":
                        itemIndex = 6;
                        if (inv.get(itemIndex).use()) {
                            map.get(14).update(3, 15);
                            so.println("The explosion causes the wall to collapse, revealing a secret passageway to the east!");
                            return map.get(14);
                        }
                }
                so.println("I didn't get it. What do you want to use?");
            }
        }
        return map.get(0);
    }

    public int attackCommand(ArrayList<Room> map, int r, ArrayList<Item> inv) {             //attack command
        if (commandWord.equals("attack")) {
            if (secondWord.equals("")) {
                so.println("Attack what? I don't understand.");
            } else if (secondWord.contains("with")) {                                       //checks for "with" word
                weaponWord = secondWord.substring(secondWord.indexOf("with") + 5);          //5 indexes for "with" plus "_"
                secondWord = secondWord.substring(0, secondWord.indexOf(" "));
                if (map.get(r).monster().tag().equals("enemy")) {                           //checks if it's dead or not (enemy)
                    switch (weaponWord) {
                        case "rusty sword":
                            if (inv.get(7).getCount() == 1) {
                                so.println("You attacked the " + map.get(r).monster().name() + " for 5 damage!");
                                map.get(r).monster().damage(5);
                                return map.get(r).monster().attack();
                            }
                        case "iron sword":
                            if (inv.get(8).getCount() == 1) {
                                so.println("You attacked the " + map.get(r).monster().name() + " for 15 damage!");
                                map.get(r).monster().damage(15);
                                return map.get(r).monster().attack();
                            }
                        case "grenade":
                            if (inv.get(9).getCount() == 1) {
                                so.println("You attacked the " + map.get(r).monster().name() + " for 50 damage!");
                                map.get(r).monster().damage(50);
                                inv.get(9).destroy();
                                return map.get(r).monster().attack();
                            }
                        }
                        so.println("Attack with what?");
                    }
                }
            }
        return 0;
        }

    public void lookCommand(ArrayList<Room> map, int r) {
        if (commandWord.equals("look")) {
            so.println(map.get(r).description());
            if (map.get(r).item() != null) {
                switch (r) {
                    case 1 -> so.println(map.get(r).look("Something shines in the corner of the room. It's... a bronze key!")); //add specific look instructions for room
                    case 2 -> so.println(map.get(r).look("Mounted on the wall covered in cobwebs is a rusty sword! It's frail, but you can still use it."));
                    case 7 -> so.println(map.get(r).look("Something shiny radiate a glow through the crack in the wall. It seems to be a jeweled skull with something green embedded and seems to open... " +
                            "\nYou can probably reach it if you try."));
                    case 9 -> so.println(map.get(r).look("There's an open chest in the middle of the room where a shiny, iron sword lays. Looks very sharp!"));
                    case 12 -> so.println(map.get(r).look("A chain dangles from the ceiling that is tied around a golden key. You're tall enough to reach it."));
                    case 13 -> so.println(map.get(r).look("There's a huge bomb by the corner of the room. It actually seems safe to pick up."));
                    case 16 -> so.println(map.get(r).look("There's a grenade sitting idly on the desk."));
                    case 17 -> so.println(map.get(r).look("There seems to what appears to be a bone in a crack. Upon further inspection, it's a skeleton key."));
                    case 23 -> so.println(map.get(r).look("There's a shrine by the southwest corner that holds a red gem. Might be worth carrying."));
                    case 24 -> so.println(map.get(r).look("There's another shrine that holds a blue gem. It almost blinds you as you look at it."));
                    case 18 -> so.println(map.get(r).look("By pure luck, there's some rotten food stuffed in some random cabinet. Lucky you!"));
                    case 3, 8, 11, 19, 22, 25 -> so.println(map.get(r).look("There's food here."));
                }
            }
            switch (r) {
                case 5 -> so.println(map.get(r).look("The bronze gate has a giant keyhole by the rusty handle."));
                case 10 -> so.println(map.get(r).look("There's a skull-imprinted hole by the side of the skeleton gates. " +
                        "\nMaybe something can be put in there."));
                case 14 -> so.println(map.get(r).look("In the east, the wall is cracked and seems to be almost falling over. " +
                        "\nA bomb might make it fall over."));
                case 15 -> so.println(map.get(r).look("A golden door awaits before you. There's a giant golden keyhole by the right of it."));
                case 20 -> so.println(map.get(r).look("At the east, a skeleton door sits ominously, the skull watching you from above on the door frame. " +
                        "\nA keyhole sits right in the middle."));
                case 24 -> so.println(map.get(r).look("There's a green pressure plate by the door in the south."));
                case 25 -> so.println(map.get(r).look("There's another red pressure plate by another door in the east."));
                case 26 -> so.println(map.get(r).look("A blue pressure plate sits by the door in the north. " +
                        "\nIt seems to be the last obstacle."));
            }
            if (map.get(r).monster() != null) {
                if (map.get(r).monster().tag().equals("enemy")) {
                    so.println("There's a " + map.get(r).monster().name() + "! " + map.get(r).monster().description());
                } else if (map.get(r).monster().tag().equals("dead")) {
                    so.println("The " + map.get(r).monster().name() + " is dead.");
                }
            }
        }
    }

    public int getItemIndex() { return itemIndex; }     //other getters
    public int getDirection() {                         //THIS DUMB BUG TOOK ALMOST 2 HOURS I DON'T KNOW ANY OTHER WAY TO IMPLEMENT THIS WITHOUT PULLING MY HAIR
        if (direction >= 0) {
            return direction;
        }
        return -999;
    }
}

abstract class GameObject{
    String name = "Unknown name...";
    String description = "Unknown description...";
    String tag = "Unknown type...";
    public String name() { return name; }
    public String description() { return description; }
    public String tag() { return tag; }
}

class Item {
    String name = "Unknown name...";
    String description;
    String tag;
    public String name() { return name; }
    public String description() { return description; }

    private int count = 0;
    public Item() {}
    public Item(String name, String description, String tag) {
        this.name = name;
        this.description = description;
        this.tag = tag;
    }

    public int getCount() { return count; }
    public int count() { return count = 1; }    //ensure that count is 1, when item is picked up
    public void setCount(int i) { count = i; }
    public void destroy() { count = 0; }        //changes the count to 0 so it can't be usable in any way

    public boolean use() {                      //check if the item is usable, return true
        if (count == 1) {
            return true;
        } else if (count == 0) {
            return false;
        }
        return false;
    }

}

class Food extends Item {
    private int restoreHP = 20;
    private int count = 0;
    public Food(String name, String description, String tag) {
        this.name = name;
        this.description = description;
        this.tag = tag;
    }
    public int getCount() { return count; }
    public int count() { return count = count + 1; }                        //different from item's count, adds to count instead
    public void consume() { if (count > 0) { count -= 1; } }                //like destroy, but sub from count instead in case more than 1 count
    public int restoreHP() { return restoreHP; }
}

class Weapon extends Item {
    private int damage;
    private int count;

    public Weapon(String name, String description, String tag, int damage) {
        this.name = name;
        this.description = description;
        this.tag = tag;
        this.damage = damage;
    }
    public void destroy() { count = 0; }
    public int damage() { return damage; }
}

class Monster extends GameObject {
    private int health, attack;

    public Monster(String name, String description, String tag) {
        this.name = name;
        this.description = description;
        this.tag = tag;
    }

    public Monster(String name, String description, String tag, int health, int attack) {
        this.name = name;
        this.description = description;
        this.tag = tag;
        this.health = health;
        this.attack = attack;
    }
    public int getCount() { return 0; }
    public void damage(int d) {
        health = health - d;
        if (health <= 0) {
            health = 0;
            tag = "dead";
            so.println("You have defeated the " + name + "!");
        } else {
            so.println("It's health is at " + health);
        }
    }
    public int attack() { return attack; }
}

class Room extends GameObject {
    int N, S, W, E;
    private Item item;
    private Monster monster;
    public Item item() { return item; }
    public int getCount() { return 0; }
    public Monster monster() { return monster; }
    public int direction(int d) {           //returns the value of the directions
        switch(d) {
            case 0: return N;
            case 1: return S;
            case 2: return W;
            case 3: return E;
        }
        return -1;
    }
    public int update(int d, int r) {       //update method to allow player to pass through a door/bomb wall
        switch(d) {
            case 0: N = r; return N;
            case 1: S = r; return S;
            case 2: W = r; return W;
            case 3: E = r; return E;
        }
        return -1;
    }
    public void setItem(Item newItem) { item = newItem; }

    public String look(String update) { return update; }  //when the user decides to look at the room, update it when needed.

    public Room(String description, int N, int S, int W, int E, Item item) {
        this.description = description;
        this.N = N; this.S = S; this.W = W; this.E = E;
        this.item = item;
    }

    public Room(String description, int N, int S, int W, int E) {
        this.description = description;
        this.N = N; this.S = S; this.W = W; this.E = E;
    }

    public int add() {
        return item.count();
    }
    public void addMonster(Monster newMonster) { monster = newMonster; }
    public void addItem(Item newItem) { item = newItem; }
    public void removeItem() { item = null; }
}


//TEST 1

//        WELCOME TO THE LEGEND OF THE DARKEST TOWER!
//        s=======================================================================================================
//        You are exiled and dropped into an abandoned prison tower, north of Feurough. Facing certain death,
//        you are left to survive in a dark and twisted tower that is filled with whatever lurks in the shadows.
//        Do whatever you can to get through the rooms and advance up the levels
//        and reach the top where there is salvation. Use your wits and find anything to get through it all...
//        When your back is against the wall and all hope is lost, type help.
//        =======================================================================================================
//        You wake from unconsciousness and feel dizzy as you stand up.
//        You're in a dark room. Your hand is holding onto an unlit torch.
//        What is your command?
//        light torch
//        Let there be light.
//        The torch is lit and all you see is two wooden doors. One door has a broken lock and leads to
//        the south while the other has missing hinges and leads to the east.
//        What is your command?
//        look
//        The torch is lit and all you see is two wooden doors. One door has a broken lock and leads to
//        the south while the other has missing hinges and leads to the east.
//        Something shines in the corner of the room. It's... a bronze key!
//        What is your command?
//        take bronze key
//        Taking bronze key.
//        What is your command?
//        help
//        SALVATION: Reach the highest level of the game to escape the prison.
//        The game works almost like Zork.
//        Input these commands whenever useful, otherwise the game will tell you.
//        In any case you made a command and nothing is returned, probably not the solution.
//        Try another command.
//        COMMANDS:
//        HELP: help
//        Displays instructions on how to play the game.
//        LIGHT: light torch
//        Light the torch.
//        LOOK: look
//        Tells you what you need to know in the room and enemies afoot.
//        GO: go [direction]
//        Go any direction if possible. Directions are north(n), south(s), east(e) and west(w).
//        TAKE: take [item]
//        If there's an item in the room, you can pick it up.
//        USE: use [item]
//        Use the item when necessary for puzzles.
//        OPEN: open [item]
//        Open a special item when necessary.
//        CHECK INVENTORY: inventory
//        Shows your inventory.
//        EXAMINE: examine [item]
//        Examines the item in your inventory, if you have any.
//        EAT: eat [food]
//        If you have food, eat it to get some health back.
//        ATTACK: attack [monster] with [weapon] *OR* attack with [weapon]
//        Everytime you attack, you will receive damage from the monster. Be careful.
//        Attacking with only weapon and you will attack the monster in the room nonetheless.
//        QUIT: quit *OR* exit
//        Press [ENTER] to input your command.
//        Capitalization doesn't matter. And please, don't put a space after your command.
//        It's too dark to do anything.
//        What is your command?
//        inventory
//        This is what's in your bag so far...
//        bronze key
//        What is your command?
//        go south
//        You enter a room and can faintly make out the appearance of three doors.
//        One to the east, one to the west and one to the north of the room
//        What is your command?
//        look
//        You enter a room and can faintly make out the appearance of three doors.
//        One to the east, one to the west and one to the north of the room
//        What is your command?
//        go east
//        You have now entered another dark and grimey room. You cannot help but notice a door to the
//        north and another door to the west. The door to the north has blood stains all over while the one to the west seems untouched.
//        What is your command?
//        look
//        You have now entered another dark and grimey room. You cannot help but notice a door to the
//        north and another door to the west. The door to the north has blood stains all over while the one to the west seems untouched.
//        There's food here.
//        What is your command?
//        take food
//        Taking food.
//        What is your command?
//        inventory
//        This is what's in your bag so far...
//        bronze key
//        food
//        What is your command?
//        examine food
//        Moldy food that's been left to rot for ages... it's surprisingly still good to eat!
//        What is your command?
//        go north
//        You've stumbled into another room filled with dust and bugs. All you can see are two doors in
//        the distance. One door, covered in moss, goes west while the other door, covered in webs goes south.
//        What is your command?
//        look
//        You've stumbled into another room filled with dust and bugs. All you can see are two doors in
//        the distance. One door, covered in moss, goes west while the other door, covered in webs goes south.
//        Mounted on the wall covered in cobwebs is a rusty sword! It's frail, but you can still use it.
//        There's a skeleton! A bony reminiscent of a lifetime's past, animated to that of a bare bone-like creature.
//        What is your command?
//        take rusty sword
//        Taking rusty sword.
//        What is your command?
//        examine rusty sword
//        A heavy sword that's hard to swing, but feels like it could do some damage!
//        What is your command?
//        attack skeleton with rusty sword
//        You attacked the skeleton for 5 damage!
//        It's health is at 10
//        Your health is at 95
//        What is your command?
//        attack with rusty sword
//        You attacked the skeleton for 5 damage!
//        It's health is at 5
//        Your health is at 90
//        What is your command?
//        attack with rusty sword
//        You attacked the skeleton for 5 damage!
//        You have defeated the skeleton!
//        Your health is at 85
//        What is your command?
//        go west
//        The torch is lit and all you see is two wooden doors. One door has a broken lock and leads to
//        the south while the other has missing hinges and leads to the east.
//        What is your command?
//        look
//        The torch is lit and all you see is two wooden doors. One door has a broken lock and leads to
//        the south while the other has missing hinges and leads to the east.
//        What is your command?
//        go east
//        You've stumbled into another room filled with dust and bugs. All you can see are two doors in
//        the distance. One door, covered in moss, goes west while the other door, covered in webs goes south.
//        What is your command?
//        go south
//        You have now entered another dark and grimey room. You cannot help but notice a door to the
//        north and another door to the west. The door to the north has blood stains all over while the one to the west seems untouched.
//        What is your command?
//        go west
//        You enter a room and can faintly make out the appearance of three doors.
//        One to the east, one to the west and one to the north of the room
//        What is your command?
//        look
//        You enter a room and can faintly make out the appearance of three doors.
//        One to the east, one to the west and one to the north of the room
//        What is your command?
//        go west
//        You come up to another room. The walls are plain and smooth.
//        There's a bronze gate to the south. There's also a door to the east.
//        What is your command?
//        look
//        You come up to another room. The walls are plain and smooth.
//        There's a bronze gate to the south. There's also a door to the east.
//        The bronze gate has a giant keyhole by the rusty handle.
//        What is your command?
//        use bronze key
//        The bronze gate creaks open, the rust falling off its hinges...
//        You used the bronze key.
//        What is your command?
//        go south
//        Going through the bronze gate leads you up a flight of stairs to another floor.
//        Still enshrouded in darkness due to the lowlight of the torch, you can only see
//        doors on the north and east side of the room. The staircase at the south leads back to the lower level.
//        What is your command?
//        look
//        Going through the bronze gate leads you up a flight of stairs to another floor.
//        Still enshrouded in darkness due to the lowlight of the torch, you can only see
//        doors on the north and east side of the room. The staircase at the south leads back to the lower level.
//        What is your command?
//        go east
//        You enter this room and take a wide look around. Now there are three distinct doors.
//        There is one west which is a faint blue color, one south that has words in big letters that say DO NOT ENTER,
//        and the one east with a red light coming from the bottom.
//        What is your command?
//        look
//        You enter this room and take a wide look around. Now there are three distinct doors.
//        There is one west which is a faint blue color, one south that has words in big letters that say DO NOT ENTER,
//        and the one east with a red light coming from the bottom.
//        There's food here.
//        What is your command?
//        take food
//        Taking food.
//        What is your command?
//        go west
//        You enter the room and see a dimly lit area. You can make out the doors at the south and east side of the room.
//        What is your command?
//        look
//        You enter the room and see a dimly lit area. You can make out the doors at the south and east side of the room.
//        Something shiny radiate a glow through the crack in the wall. It seems to be a jeweled skull with something green embedded inside and seems to open...
//        You can probably reach it if you try.
//        What is your command?
//        take jeweled skull
//        Taking jeweled skull.
//        What is your command?
//        examine jeweled skull
//        A jewel-encrusted skull with a bit of weight to it. It's too shiny to let go. The top of its head looks like it can open.
//        What is your command?
//        open jeweled skull
//        The skull opens and plops out a green gem onto the floor. Might be useful to take.
//        What is your command?
//        take green gem
//        Taking green gem.
//        What is your command?
//        inventory
//        This is what's in your bag so far...
//        jeweled skull
//        rusty sword
//        food
//        green gem
//        What is your command?
//        go east
//        You enter this room and take a wide look around. Now there are three distinct doors.
//        There is one west which is a faint blue color, one south that has words in big letters that say DO NOT ENTER,
//        and the one east with a red light coming from the bottom.
//        What is your command?
//        go east
//        This room has a funky smell to it. There are also two doors, one to the west and one to the south.
//        What is your command?
//        look
//        This room has a funky smell to it. There are also two doors, one to the west and one to the south.
//        There's an open chest in the middle of the room where a shiny, iron sword lays. Looks very sharp!
//        There's a skeleton! A bony reminiscent of a lifetime's past, animated to that of a bare bone-like creature.
//        What is your command?
//        take iron sword
//        Taking iron sword.
//        What is your command?
//        examine iron sword
//        A sharp and shiny that can hack and slash anything in its path!
//        What is your command?
//        attack skeleton with iron sword
//        You attacked the skeleton for 15 damage!
//        You have defeated the skeleton!
//        Your health is at 80
//        What is your command?
//        go south
//        You see two gates, both of which look as though they're made of bones.
//        You approach the west gate and realize that the other side had collapsed and is a dead end.
//        The east gate seems to be the only passage.
//        What is your command?
//        look
//        You see two gates, both of which look as though they're made of bones.
//        You approach the west gate and realize that the other side had collapsed and is a dead end.
//        The east gate seems to be the only passage.
//        There's a skull-imprinted hole by the side of the skeleton gates.
//        Maybe something can be put in there.
//        What is your command?
//        go go east
//        What is your command?
//        go east
//        You cannot go here...
//        What is your command?
//        look
//        You see two gates, both of which look as though they're made of bones.
//        You approach the west gate and realize that the other side had collapsed and is a dead end.
//        The east gate seems to be the only passage.
//        There's a skull-imprinted hole by the side of the skeleton gates.
//        Maybe something can be put in there.
//        What is your command?
//        use jeweled skull
//        The skeleton gates collapses and turns to dust, as if by magic!
//        A hallway at the east leads upwards.
//        You used the jeweled skull.
//        What is your command?
//        go east
//        Going through the skeleton gate leads you up another flight of stairs onto another floor.
//        All you can see is a door on the south side and east side.
//        What is your command?
//        look
//        Going through the skeleton gate leads you up another flight of stairs onto another floor.
//        All you can see is a door on the south side and east side.
//        There's food here.
//        What is your command?
//        take food
//        Taking food.
//        What is your command?
//        go east
//        The room's north side is filled with empty cells. Ragged clothing is scattered throughout the floor and not a soul is found.
//        There are doors on the west, south and east side of the room.
//        What is your command?
//        look
//        The room's north side is filled with empty cells. Ragged clothing is scattered throughout the floor and not a soul is found.
//        There are doors on the west, south and east side of the room.
//        A chain dangles from the ceiling that is tied around a golden key. You're tall enough to reach it.
//        What is your command?
//        take golden key
//        Taking golden key.
//        What is your command?
//        examine golden key
//        A gold key. Looks like it belongs to a golden keyhole.
//        What is your command?
//        go east
//        A giant cell sits at the west of the room. It's too far deep to tell what's inside,
//        but you hear a distant growl from darkness. You dare not to get closer.
//        The passageways at north and south lead you back to the cell rooms while the east leads you to a hallway.
//        What is your command?
//        look
//        A giant cell sits at the west of the room. It's too far deep to tell what's inside,
//        but you hear a distant growl from darkness. You dare not to get closer.
//        The passageways at north and south lead you back to the cell rooms while the east leads you to a hallway.
//        In the east, the wall is cracked and seems to be almost falling over.
//        A bomb might make it fall over.
//        What is your command?
//        go south
//        This room looks very similar to the previous room where empty cells fill the west and south side.
//        Hay from the makeshift prison beds scatter across the stone-cold floor.
//        Around the room, there seems to three stone doors.A door west, south, and east.
//        What is your command?
//        look
//        This room looks very similar to the previous room where empty cells fill the west and south side.
//        Hay from the makeshift prison beds scatter across the stone-cold floor.
//        Around the room, there seems to three stone doors.A door west, south, and east.
//        There's a huge bomb by the corner of the room. It actually seems safe to pick up.
//        There's a grue! It's a grue! ...What the Zork is a grue?
//        What is your command?
//        take bomb
//        Taking bomb.
//        What is your command?
//        examine bomb
//        A really big bomb. Like, comically huge. Imagine big, but bigger! Can destroy a cracked wall.
//        What is your command?
//        attack grue with iron sword
//        You attacked the grue for 15 damage!
//        It's health is at 10
//        Your health is at 75
//        What is your command?
//        attack with iron sword
//        You attacked the grue for 15 damage!
//        You have defeated the grue!
//        Your health is at 70
//        What is your command?
//        go east
//        A giant cell sits at the west of the room. It's too far deep to tell what's inside,
//        but you hear a distant growl from darkness. You dare not to get closer.
//        The passageways at north and south lead you back to the cell rooms while the east leads you to a hallway.
//        What is your command?
//        look
//        A giant cell sits at the west of the room. It's too far deep to tell what's inside,
//        but you hear a distant growl from darkness. You dare not to get closer.
//        The passageways at north and south lead you back to the cell rooms while the east leads you to a hallway.
//        In the east, the wall is cracked and seems to be almost falling over.
//        A bomb might make it fall over.
//        What is your command?
//        use bomb
//        The explosion causes the wall to collapse, revealing a secret passageway to the east!
//        You used the bomb.
//        What is your command?
//        go east
//        It's a short hallway corner where at the south is a golden door. West takes you back to the cell rooms.
//        What is your command?
//        look
//        It's a short hallway corner where at the south is a golden door. West takes you back to the cell rooms.
//        A golden door awaits before you. There's a giant golden keyhole by the right of it.
//        What is your command?
//        inventory
//        This is what's in your bag so far...
//        golden key
//        rusty sword
//        iron sword
//        food
//        green gem
//        What is your command?
//        use golden k ey
//        I didn't get it. What do you want to use?
//        I didn't get it. What do you want to use?
//        You can't use this here.
//        What is your command?
//        use golden key
//        The golden door shines and opens gracefully, revealing another set of stairs south.
//        You used the golden key.
//        What is your command?
//        go south
//        The warden's quarters. There's no one hear and the room seems to be long abandoned.
//        There's a door at the north and at the south.
//        What is your command?
//        look
//        The warden's quarters. There's no one hear and the room seems to be long abandoned.
//        There's a door at the north and at the south.
//        There's a grenade sitting idly on the desk.
//        What is your command?
//        take grenade
//        Taking grenade.
//        What is your command?
//        examine grenade
//        An ancient looking relic that has a pull-able pin to it. It's been scribed with '1943 MK2 Grenade.' Attack to throw it. One use.
//        What is your command?
//        go south
//        This seems to be the barracks. Bunk beds by rows lay firm with dirty blankets
//        and stained pillowcases while the weaponry is ransacked and bare empty. Not much use from here.
//        The passageway at north leads back to the starting room. There is a passage at east and south.
//        What is your command?
//        look
//        This seems to be the barracks. Bunk beds by rows lay firm with dirty blankets
//        and stained pillowcases while the weaponry is ransacked and bare empty. Not much use from here.
//        The passageway at north leads back to the starting room. There is a passage at east and south.
//        There's food here.
//        What is your command?
//        take food
//        Taking food.
//        What is your command?
//        examine food
//        Moldy food that's been left to rot for ages... it's surprisingly still good to eat!
//        What is your command?
//        go south
//        The room smells like rotten meat. The walls are chiseled and have the color of charcoal.
//        By the east sits a menacing skeleton door. There are passageways at west, north, and south.
//        What is your command?
//        look
//        The room smells like rotten meat. The walls are chiseled and have the color of charcoal.
//        By the east sits a menacing skeleton door. There are passageways at west, north, and south.
//        At the east, a skeleton door sits ominously, the skull watching you from above on the door frame.
//        A keyhole sits right in the middle.
//        What is your command?
//        go north
//        A seemingly long hallway where the walls are filled with ripped paintings and tattered tapestry.
//        Cabinets by the walls have wood-rot and appear to have nothing inside.
//        The hallway has a clearing at north and south.
//        What is your command?
//        look
//        A seemingly long hallway where the walls are filled with ripped paintings and tattered tapestry.
//        Cabinets by the walls have wood-rot and appear to have nothing inside.
//        The hallway has a clearing at north and south.
//        By pure luck, there's some rotten food stuffed in some random cabinet. Lucky you!
//        What is your command?
//        take food
//        Taking food.
//        What is your command?
//        go north
//        It's a seemingly normal room where the ceiling depicts a faded painting of what seems to be
//        a starry sky filled with storm clouds and clashing lightning strikes.
//        This room has passageways all-around.
//        What is your command?
//        look
//        It's a seemingly normal room where the ceiling depicts a faded painting of what seems to be
//        a starry sky filled with storm clouds and clashing lightning strikes.
//        This room has passageways all-around.
//        There seems to what appears to be a bone in a crack. Upon further inspection, it's a skeleton key.
//        There's a goblin! A vile, green-skinned parasite that has warts and dirt covering every inch of its filthy frame.
//        What is your command?
//        take skeleton key
//        Taking skeleton key.
//        What is your command?
//        attack goblin with iron sword
//        You attacked the goblin for 15 damage!
//        It's health is at 30
//        Your health is at 60
//        What is your command?
//        attack with iron sword
//        You attacked the goblin for 15 damage!
//        It's health is at 15
//        Your health is at 50
//        What is your command?
//        attack with iron sword
//        You attacked the goblin for 15 damage!
//        You have defeated the goblin!
//        Your health is at 40
//        What is your command?
//        use food
//        I didn't get it. What do you want to use?
//        I didn't get it. What do you want to use?
//        You can't use this here.
//        What is your command?
//        eat food
//        Your health is at 60
//        What is your command?
//        eat food
//        Your health is at 80
//        What is your command?
//        look
//        It's a seemingly normal room where the ceiling depicts a faded painting of what seems to be
//        a starry sky filled with storm clouds and clashing lightning strikes.
//        This room has passageways all-around.
//        The goblin is dead.
//        What is your command?
//        go north
//        A seemingly long hallway where the walls are filled with ripped paintings and tattered tapestry.
//        Cabinets by the walls have wood-rot and appear to have nothing inside.
//        The hallway has a clearing at north and south.
//        What is your command?
//        look
//        A seemingly long hallway where the walls are filled with ripped paintings and tattered tapestry.
//        Cabinets by the walls have wood-rot and appear to have nothing inside.
//        The hallway has a clearing at north and south.
//        What is your command?
//        go north
//        It's a seemingly normal room where the ceiling depicts a faded painting of what seems to be
//        a starry sky filled with storm clouds and clashing lightning strikes.
//        This room has passageways all-around.
//        What is your command?
//        go south
//        This seems to be the barracks. Bunk beds by rows lay firm with dirty blankets
//        and stained pillowcases while the weaponry is ransacked and bare empty. Not much use from here.
//        The passageway at north leads back to the starting room. There is a passage at east and south.
//        What is your command?
//        look
//        This seems to be the barracks. Bunk beds by rows lay firm with dirty blankets
//        and stained pillowcases while the weaponry is ransacked and bare empty. Not much use from here.
//        The passageway at north leads back to the starting room. There is a passage at east and south.
//        What is your command?
//        go south
//        The room smells like rotten meat. The walls are chiseled and have the color of charcoal.
//        By the east sits a menacing skeleton door. There are passageways at west, north, and south.
//        What is your command?
//        look
//        The room smells like rotten meat. The walls are chiseled and have the color of charcoal.
//        By the east sits a menacing skeleton door. There are passageways at west, north, and south.
//        At the east, a skeleton door sits ominously, the skull watching you from above on the door frame.
//        A keyhole sits right in the middle.
//        What is your command?
//        examine skeleton key
//        A key with a head shaped like a skull. Very cool.
//        What is your command?
//        use skeleton key
//        The skeleton door opens like a chatterbox filled with bones. It stops just enough for you to go through.
//        You used the skeleton key.
//        What is your command?
//        look
//        The room smells like rotten meat. The walls are chiseled and have the color of charcoal.
//        By the east sits a menacing skeleton door. There are passageways at west, north, and south.
//        At the east, a skeleton door sits ominously, the skull watching you from above on the door frame.
//        A keyhole sits right in the middle.
//        What is your command?
//        go east
//        It's a plain room with cobblestone all-around, wet from the moisture as the sounds of screaming is heard from the south.
//        West leads back to a staircase from the skeleton door. At the north there's a passageway and at the south is an open door.
//        What is your command?
//        look
//        It's a plain room with cobblestone all-around, wet from the moisture as the sounds of screaming is heard from the south.
//        West leads back to a staircase from the skeleton door. At the north there's a passageway and at the south is an open door.
//        What is your command?
//        go south
//        Walls around are covered in stained blood and chipped scratches from other... beings.
//        The entrance at the west goes back to the first room. There's another broken doorway at the east.
//        What is your command?
//        look
//        Walls around are covered in stained blood and chipped scratches from other... beings.
//        The entrance at the west goes back to the first room. There's another broken doorway at the east.
//        There's another shrine that holds a blue gem. It almost blinds you as you look at it.
//        There's a green pressure plate by the door in the south.
//        What is your command?
//        take blue gem
//        Taking blue gem.
//        What is your command?
//        examine blue gem
//        A blue gem that's close to the color of the ocean. Its sparkle lulls you close to a calmness.
//        What is your command?
//        use green gem
//        The pressure plate clicks in satisfaction. The door at the south opens with a clunk.
//        You used the green gem.
//        What is your command?
//        go east
//        The walls are wet from moisture and heat rises through the cracks in the floor.
//        There's a passageway at north and south.
//        What is your command?
//        look
//        The walls are wet from moisture and heat rises through the cracks in the floor.
//        There's a passageway at north and south.
//        There's a shrine by the southwest corner that holds a red gem. Might be worth carrying.
//        What is your command?
//        take red gem
//        Taking red gem.
//        What is your command?
//        examine red gem
//        A shiny red gem that sparkles blindly in any hint of light. Might be worth carrying.
//        What is your command?
//        go north
//        Chains and cages hung high from the ceiling, filled with bones and fleshy bits.
//        There are passageways at west and east.
//        What is your command?
//        look
//        Chains and cages hung high from the ceiling, filled with bones and fleshy bits.
//        There are passageways at west and east.
//        There's food here.
//        What is your command?
//        take food
//        Taking food.
//        What is your command?
//        go east
//        The walls are wet from moisture and heat rises through the cracks in the floor.
//        There's a passageway at north and south.
//        What is your command?
//        look
//        The walls are wet from moisture and heat rises through the cracks in the floor.
//        There's a passageway at north and south.
//        What is your command?
//        go north
//        Chains and cages hung high from the ceiling, filled with bones and fleshy bits.
//        There are passageways at west and east.
//        What is your command?
//        look
//        Chains and cages hung high from the ceiling, filled with bones and fleshy bits.
//        There are passageways at west and east.
//        What is your command?
//        go south
//        The room is covered in a dark red hue. It feels hopeless in here.
//        The passageway at the north goes back. There seems to be no other path... or so it seems.
//        What is your command?
//        look
//        The room is covered in a dark red hue. It feels hopeless in here.
//        The passageway at the north goes back. There seems to be no other path... or so it seems.
//        There's food here.
//        There's another red pressure plate by another door in the east.
//        There's a demon! A demon that reeks in evil and darkness, waiting for you to succumb to its devilish needs. The sight of it leaves you numb.
//        What is your command?
//        take food
//        Taking food.
//        What is your command?
//        use red gem
//        The pressure plate clicks in satisfaction, the walls opening to another pathway at the east.
//        You used the red gem.
//        What is your command?
//        attack demon with iron sword
//        You attacked the demon for 15 damage!
//        It's health is at 30
//        Your health is at 65
//        What is your command?
//        attack with iron sword
//        You attacked the demon for 15 damage!
//        It's health is at 15
//        Your health is at 50
//        What is your command?
//        attack with iron sword
//        You attacked the demon for 15 damage!
//        You have defeated the demon!
//        Your health is at 35
//        What is your command?
//        eat food
//        Your health is at 55
//        What is your command?
//        eat food
//        Your health is at 75
//        What is your command?
//        eat food
//        Your health is at 95
//        What is your command?
//        go east
//        The walls are covered high in gold coins and treasure while the room reeks in reptile musk and hot steam mixed unpleasantly.
//        The hallway at west leads back.
//        What is your command?
//        look
//        The walls are covered high in gold coins and treasure while the room reeks in reptile musk and hot steam mixed unpleasantly.
//        The hallway at west leads back.
//        A blue pressure plate sits by the door in the north.
//        It seems to be the last obstacle.
//        There's a dragon! It's a large bodied reptile with bat-like wings equipped with sharp talons and a huge barbed tail
//        What is your command?
//        use blue gem
//        The pressure plate clicks in satisfaction as a blinding lights cracks through an opening in the wall at the north.
//        You are thin enough to go through.
//        You used the blue gem.
//        What is your command?
//        go north
//        You can't go through! There's a monster!
//        What is your command?
//        attack dragon with iron sword
//        You attacked the dragon for 15 damage!
//        It's health is at 45
//        Your health is at 75
//        What is your command?
//        attack with grenade
//        You attacked the dragon for 50 damage!
//        You have defeated the dragon!
//        Your health is at 55
//        What is your command?
//        eat food
//        Your health is at 75
//        What is your command?
//        eat food
//        Your health is at 95
//        What is your command?
//        eat food
//        There's nothing to eat.
//        What is your command?
//        go north
//        You've finally reached the top of the tower.
//        You take a look around the world around you and see the sun setting, hear birds chirping, and can smell the warm and earthy air.
//        You've accomplished what no man has ever done before.
//        You've successfully beaten THE DARKEST TOWER.
//
//        Process finished with exit code 0

//TEST 2
//        WELCOME TO THE LEGEND OF THE DARKEST TOWER!
//        You are exiled and dropped into an abandoned prison tower, north of Feurough. Facing certain death,
//        you are left to survive in a dark and twisted tower that is filled with whatever lurks in the shadows.
//        Do whatever you can to get through the rooms and advance up the levels
//        and reach the top where there is salvation. Use your wits and find anything to get through it all...
//        When your back is against the wall and all hope is lost, type help.
//        =======================================================================================================
//        You wake from unconsciousness and feel dizzy as you stand up.
//        You're in a dark room. Your hand is holding onto an unlit torch.
//        What is your command?
//        help
//        SALVATION: Reach the highest level of the game to escape the prison.
//        The game works almost like Zork.
//        Input these commands whenever useful, otherwise the game will tell you.
//        In any case you made a command and nothing is returned, probably not the solution.
//        Try another command.
//        COMMANDS:
//        HELP: help
//        Displays instructions on how to play the game.
//        LIGHT: light torch
//        Light the torch.
//        LOOK: look
//        Tells you what you need to know in the room and enemies afoot.
//        GO: go [direction]
//        Go any direction if possible. Directions are north(n), south(s), east(e) and west(w).
//        TAKE: take [item]
//        If there's an item in the room, you can pick it up.
//        USE: use [item]
//        Use the item when necessary for puzzles.
//        OPEN: open [item]
//        Open a special item when necessary.
//        CHECK INVENTORY: inventory
//        Shows your inventory.
//        EXAMINE: examine [item]
//        Examines the item in your inventory, if you have any.
//        EAT: eat [food]
//        If you have food, eat it to get some health back.
//        ATTACK: attack [monster] with [weapon] *OR* attack with [weapon]
//        Everytime you attack, you will receive damage from the monster. Be careful.
//        Attacking with only weapon and you will attack the monster in the room nonetheless.
//        QUIT: quit *OR* exit
//        Press [ENTER] to input your command.
//        Capitalization doesn't matter. And please, don't put a space after your command.
//        It's too dark to do anything.
//        What is your command?
//        light torch
//        Let there be light.
//        The torch is lit and all you see is two wooden doors. One door has a broken lock and leads to
//        the south while the other has missing hinges and leads to the east.
//        What is your command?
//        look
//        The torch is lit and all you see is two wooden doors. One door has a broken lock and leads to
//        the south while the other has missing hinges and leads to the east.
//        Something shines in the corner of the room. It's... a bronze key!
//        What is your command?
//        take bronze key
//        Taking bronze key.
//        What is your command?
//        examine bronze key
//        It's a bronze key. Still shiny after so many years. Must have a use here.
//        What is your command?
//        go east
//        You've stumbled into another room filled with dust and bugs. All you can see are two doors in
//        the distance. One door, covered in moss, goes west while the other door, covered in webs goes south.
//        What is your command?
//        look
//        You've stumbled into another room filled with dust and bugs. All you can see are two doors in
//        the distance. One door, covered in moss, goes west while the other door, covered in webs goes south.
//        Mounted on the wall covered in cobwebs is a rusty sword! It's frail, but you can still use it.
//        There's a skeleton! A bony reminiscent of a lifetime's past, animated to that of a bare bone-like creature.
//        What is your command?
//        take rusty sword
//        Taking rusty sword.
//        What is your command?
//        examine rusty sword
//        A heavy sword that's hard to swing, but feels like it could do some damage!
//        What is your command?
//        attack skeleton with rusty sword
//        You attacked the skeleton for 5 damage!
//        It's health is at 10
//        Your health is at 95
//        What is your command?
//        attack with rusty sword
//        You attacked the skeleton for 5 damage!
//        It's health is at 5
//        Your health is at 90
//        What is your command?
//        attack with sword
//        Attack with what?
//        Your health is at 90
//        What is your command?
//        attack with rusty sword
//        You attacked the skeleton for 5 damage!
//        You have defeated the skeleton!
//        Your health is at 85
//        What is your command?
//        look
//        You've stumbled into another room filled with dust and bugs. All you can see are two doors in
//        the distance. One door, covered in moss, goes west while the other door, covered in webs goes south.
//        The skeleton is dead.
//        What is your command?
//        go south
//        You have now entered another dark and grimey room. You cannot help but notice a door to the
//        north and another door to the west. The door to the north has blood stains all over while the one to the west seems untouched.
//        What is your command?
//        look
//        You have now entered another dark and grimey room. You cannot help but notice a door to the
//        north and another door to the west. The door to the north has blood stains all over while the one to the west seems untouched.
//        There's food here.
//        What is your command?
//        take food
//        Taking food.
//        What is your command?
//        go east
//        You cannot go here...
//        What is your command?
//        go west
//        You enter a room and can faintly make out the appearance of three doors.
//        One to the east, one to the west and one to the north of the room
//        What is your command?
//        look
//        You enter a room and can faintly make out the appearance of three doors.
//        One to the east, one to the west and one to the north of the room
//        What is your command?
//        go west
//        You come up to another room. The walls are plain and smooth.
//        There's a bronze gate to the south. There's also a door to the east.
//        What is your command?
//        look
//        You come up to another room. The walls are plain and smooth.
//        There's a bronze gate to the south. There's also a door to the east.
//        The bronze gate has a giant keyhole by the rusty handle.
//        What is your command?
//        use bronze key
//        The bronze gate creaks open, the rust falling off its hinges...
//        You used the bronze key.
//        What is your command?
//        go south
//        Going through the bronze gate leads you up a flight of stairs to another floor.
//        Still enshrouded in darkness due to the lowlight of the torch, you can only see
//        doors on the north and east side of the room. The staircase at the south leads back to the lower level.
//        What is your command?
//        north
//        What are you talking about?
//        What is your command?
//        go n
//        You enter the room and see a dimly lit area. You can make out the doors at the south and east side of the room.
//        What is your command?
//        look
//        You enter the room and see a dimly lit area. You can make out the doors at the south and east side of the room.
//        Something shiny radiate a glow through the crack in the wall. It seems to be a jeweled skull with something green embedded and seems to open...
//        You can probably reach it if you try.
//        What is your command?
//        take jeweled skull
//        Taking jeweled skull.
//        What is your command?
//        examine jeweled skull
//        A jewel-encrusted skull with a bit of weight to it. It's too shiny to let go. The top of its head looks like it can open.
//        What is your command?
//        open jeweled skull
//        The skull opens and plops out a green gem onto the floor. Might be useful to take.
//        What is your command?
//        take green gem
//        Taking green gem.
//        What is your command?
//        look
//        You enter the room and see a dimly lit area. You can make out the doors at the south and east side of the room.
//        What is your command?
//        go east
//        You enter this room and take a wide look around. Now there are three distinct doors.
//        There is one west which is a faint blue color, one south that has words in big letters that say DO NOT ENTER,
//        and the one east with a red light coming from the bottom.
//        What is your command?
//        look
//        You enter this room and take a wide look around. Now there are three distinct doors.
//        There is one west which is a faint blue color, one south that has words in big letters that say DO NOT ENTER,
//        and the one east with a red light coming from the bottom.
//        There's food here.
//        What is your command?
//        take food
//        Taking food.
//        What is your command?
//        go east
//        This room has a funky smell to it. There are also two doors, one to the west and one to the south.
//        What is your command?
//        look
//        This room has a funky smell to it. There are also two doors, one to the west and one to the south.
//        There's an open chest in the middle of the room where a shiny, iron sword lays. Looks very sharp!
//        There's a skeleton! A bony reminiscent of a lifetime's past, animated to that of a bare bone-like creature.
//        What is your command?
//        take iron sword
//        Taking iron sword.
//        What is your command?
//        attack with iron sword
//        You attacked the skeleton for 15 damage!
//        You have defeated the skeleton!
//        Your health is at 80
//        What is your command?
//        eat food
//        Your health is at 100
//        What is your command?
//        look
//        This room has a funky smell to it. There are also two doors, one to the west and one to the south.
//        The skeleton is dead.
//        What is your command?
//        inventory
//        This is what's in your bag so far...
//        jeweled skull
//        rusty sword
//        iron sword
//        food
//        green gem
//        What is your command?
//        go south
//        You see two gates, both of which look as though they're made of bones.
//        You approach the west gate and realize that the other side had collapsed and is a dead end.
//        The east gate seems to be the only passage.
//        What is your command?
//        look
//        You see two gates, both of which look as though they're made of bones.
//        You approach the west gate and realize that the other side had collapsed and is a dead end.
//        The east gate seems to be the only passage.
//        There's a skull-imprinted hole by the side of the skeleton gates.
//        Maybe something can be put in there.
//        What is your command?
//        use jeweled skull
//        The skeleton gates collapses and turns to dust, as if by magic!
//        A hallway at the east leads upwards.
//        You used the jeweled skull.
//        What is your command?
//        go east
//        Going through the skeleton gate leads you up another flight of stairs onto another floor.
//        All you can see is a door on the south side and east side.
//        What is your command?
//        go south
//        You see two gates, both of which look as though they're made of bones.
//        You approach the west gate and realize that the other side had collapsed and is a dead end.
//        The east gate seems to be the only passage.
//        What is your command?
//        go east
//        Going through the skeleton gate leads you up another flight of stairs onto another floor.
//        All you can see is a door on the south side and east side.
//        What is your command?
//        go east
//        The room's north side is filled with empty cells. Ragged clothing is scattered throughout the floor and not a soul is found.
//        There are doors on the west, south and east side of the room.
//        What is your command?
//        look
//        The room's north side is filled with empty cells. Ragged clothing is scattered throughout the floor and not a soul is found.
//        There are doors on the west, south and east side of the room.
//        A chain dangles from the ceiling that is tied around a golden key. You're tall enough to reach it.
//        What is your command?
//        take golden key
//        Taking golden key.
//        What is your command?
//        go south
//        This room looks very similar to the previous room where empty cells fill the west and south side.
//        Hay from the makeshift prison beds scatter across the stone-cold floor.
//        Door at west, south, and east.
//        What is your command?
//        look
//        This room looks very similar to the previous room where empty cells fill the west and south side.
//        Hay from the makeshift prison beds scatter across the stone-cold floor.
//        Door at west, south, and east.
//        There's a huge bomb by the corner of the room. It actually seems safe to pick up.
//        There's a grue! It's a grue! ...What the Zork is a grue?
//        What is your command?
//        take bomb
//        Taking bomb.
//        What is your command?
//        attack with iron sword
//        You attacked the grue for 15 damage!
//        It's health is at 10
//        Your health is at 95
//        What is your command?
//        attack with iron sword
//        You attacked the grue for 15 damage!
//        You have defeated the grue!
//        Your health is at 90
//        What is your command?
//        go east
//        A giant cell sits at the west of the room. It's too far deep to tell what's inside,
//        but you hear a distant growl from darkness. You dare not to get closer.
//        The passageways at north and south lead you back to the cell rooms while the east leads you to a hallway.
//        What is your command?
//        look
//        A giant cell sits at the west of the room. It's too far deep to tell what's inside,
//        but you hear a distant growl from darkness. You dare not to get closer.
//        The passageways at north and south lead you back to the cell rooms while the east leads you to a hallway.
//        In the east, the wall is cracked and seems to be almost falling over.
//        A bomb might make it fall over.
//        What is your command?
//        use bomb
//        The explosion causes the wall to collapse, revealing a secret passageway to the east!
//        You used the bomb.
//        What is your command?
//        go east
//        It's a short hallway corner where at the south is a golden door. West takes you back to the cell rooms.
//        What is your command?
//        look
//        It's a short hallway corner where at the south is a golden door. West takes you back to the cell rooms.
//        A golden door awaits before you. There's a giant golden keyhole by the right of it.
//        What is your command?
//        examine golden key
//        A gold key. Looks like it belongs to a golden keyhole.
//        What is your command?
//        use golden key
//        The golden door shines and opens gracefully, revealing another set of stairs south.
//        You used the golden key.
//        What is your command?
//        go east
//        You cannot go here...
//        What is your command?
//        go south
//        The warden's quarters. There's no one hear and the room seems to be long abandoned.
//        There's a door at the north and at the south.
//        What is your command?
//        look
//        The warden's quarters. There's no one hear and the room seems to be long abandoned.
//        There's a door at the north and at the south.
//        There's a grenade sitting idly on the desk.
//        What is your command?
//        take grenade
//        Taking grenade.
//        What is your command?
//        go south
//        This seems to be the barracks. Bunk beds by rows lay firm with dirty blankets
//        and stained pillowcases while the weaponry is ransacked and bare empty. Not much use from here.
//        The passageway at north leads back to the starting room. There is a passage at east and south.
//        What is your command?
//        look
//        This seems to be the barracks. Bunk beds by rows lay firm with dirty blankets
//        and stained pillowcases while the weaponry is ransacked and bare empty. Not much use from here.
//        The passageway at north leads back to the starting room. There is a passage at east and south.
//        There's food here.
//        What is your command?
//        take food
//        Taking food.
//        What is your command?
//        go east
//        It's a seemingly normal room where the ceiling depicts a faded painting of what seems to be
//        a starry sky filled with storm clouds and clashing lightning strikes.
//        This room has passageways all-around.
//        What is your command?
//        look
//        It's a seemingly normal room where the ceiling depicts a faded painting of what seems to be
//        a starry sky filled with storm clouds and clashing lightning strikes.
//        This room has passageways all-around.
//        There seems to what appears to be a bone in a crack. Upon further inspection, it's a skeleton key.
//        There's a goblin! A vile, green-skinned parasite that has warts and dirt covering every inch of its filthy frame.
//        What is your command?
//        take skeleton key
//        Taking skeleton key.
//        What is your command?
//        attack with iron sword
//        You attacked the goblin for 15 damage!
//        It's health is at 30
//        Your health is at 80
//        What is your command?
//        attack with iron sword
//        You attacked the goblin for 15 damage!
//        It's health is at 15
//        Your health is at 70
//        What is your command?
//        attack with iron sword
//        You attacked the goblin for 15 damage!
//        You have defeated the goblin!
//        Your health is at 60
//        What is your command?
//        eat food
//        You can't eat that.
//        You can't eat that.
//        What is your command?
//        eat food
//        Your health is at 80
//        What is your command?
//        eat food
//        Your health is at 100
//        What is your command?
//        go east
//        The room smells like rotten meat. The walls are chiseled and have the color of charcoal.
//        By the east sits a menacing skeleton door. There are passageways at west, north, and south.
//        What is your command?
//        go north
//        A seemingly long hallway where the walls are filled with ripped paintings and tattered tapestry.
//        Cabinets by the walls have wood-rot and appear to have nothing inside.
//        The hallway has a clearing at north and south.
//        What is your command?
//        look
//        A seemingly long hallway where the walls are filled with ripped paintings and tattered tapestry.
//        Cabinets by the walls have wood-rot and appear to have nothing inside.
//        The hallway has a clearing at north and south.
//        By pure luck, there's some rotten food stuffed in some random cabinet. Lucky you!
//        What is your command?
//        take food
//        Taking food.
//        What is your command?
//        go south
//        The room smells like rotten meat. The walls are chiseled and have the color of charcoal.
//        By the east sits a menacing skeleton door. There are passageways at west, north, and south.
//        What is your command?
//        look
//        The room smells like rotten meat. The walls are chiseled and have the color of charcoal.
//        By the east sits a menacing skeleton door. There are passageways at west, north, and south.
//        At the east, a skeleton door sits ominously, the skull watching you from above on the door frame.
//        A keyhole sits right in the middle.
//        What is your command?
//        use skeleton key
//        The skeleton door opens like a chatterbox filled with bones. It stops just enough for you to go through.
//        You used the skeleton key.
//        What is your command?
//        go east
//        It's a plain room with cobblestone all-around, wet from the moisture as the sounds of screaming is heard from the south.
//        West leads back to a staircase from the skeleton door. At the north there's a passageway and at the south is an open door.
//        What is your command?
//        look
//        It's a plain room with cobblestone all-around, wet from the moisture as the sounds of screaming is heard from the south.
//        West leads back to a staircase from the skeleton door. At the north there's a passageway and at the south is an open door.
//        What is your command?
//        go north
//        Chains and cages hung high from the ceiling, filled with bones and fleshy bits.
//        There are passageways at west and east.
//        What is your command?
//        look
//        Chains and cages hung high from the ceiling, filled with bones and fleshy bits.
//        There are passageways at west and east.
//        There's food here.
//        What is your command?
//        take food
//        Taking food.
//        What is your command?
//        go east
//        The walls are wet from moisture and heat rises through the cracks in the floor.
//        There's a passageway at north and south.
//        What is your command?
//        look
//        The walls are wet from moisture and heat rises through the cracks in the floor.
//        There's a passageway at north and south.
//        There's a shrine by the southwest corner that holds a red gem. Might be worth carrying.
//        What is your command?
//        take red gem
//        Taking red gem.
//        What is your command?
//        go south
//        Walls around are covered in stained blood and chipped scratches from other... beings.
//        The entrance at the west goes back to the first room. There's another broken doorway at the east.
//        What is your command?
//        look
//        Walls around are covered in stained blood and chipped scratches from other... beings.
//        The entrance at the west goes back to the first room. There's another broken doorway at the east.
//        There's another shrine that holds a blue gem. It almost blinds you as you look at it.
//        There's a green pressure plate by the door in the south.
//        What is your command?
//        use green gem
//        The pressure plate clicks in satisfaction. The door at the south opens with a clunk.
//        You used the green gem.
//        What is your command?
//        take blue gem
//        Taking blue gem.
//        What is your command?
//        go south
//        The room is covered in a dark red hue. It feels hopeless in here.
//        The passageway at the north goes back. There seems to be no other path... or so it seems.
//        What is your command?
//        inventory
//        This is what's in your bag so far...
//        red gem
//        blue gem
//        rusty sword
//        iron sword
//        grenade
//        food
//        What is your command?
//        look
//        The room is covered in a dark red hue. It feels hopeless in here.
//        The passageway at the north goes back. There seems to be no other path... or so it seems.
//        There's food here.
//        There's another red pressure plate by another door in the east.
//        There's a demon! A demon that reeks in evil and darkness, waiting for you to succumb to its devilish needs. The sight of it leaves you numb.
//        What is your command?
//        take food
//        Taking food.
//        What is your command?
//        attack with iron sword
//        You attacked the demon for 15 damage!
//        It's health is at 30
//        Your health is at 85
//        What is your command?
//        take iron sword
//        What is your command?
//        attack with iron sword
//        You attacked the demon for 15 damage!
//        It's health is at 15
//        Your health is at 70
//        What is your command?
//        attack with iron sword
//        You attacked the demon for 15 damage!
//        You have defeated the demon!
//        Your health is at 55
//        What is your command?
//        eat food
//        Your health is at 75
//        What is your command?
//        eat food
//        Your health is at 95
//        What is your command?
//        go east
//        The walls are covered high in gold coins and treasure while the room reeks in reptile musk and hot steam mixed unpleasantly.
//        The hallway at west leads back.
//        What is your command?
//        look
//        The walls are covered high in gold coins and treasure while the room reeks in reptile musk and hot steam mixed unpleasantly.
//        The hallway at west leads back.
//        A blue pressure plate sits by the door in the north.
//        It seems to be the last obstacle.
//        There's a dragon! It's a large bodied reptile with bat-like wings equipped with sharp talons and a huge barbed tail
//        What is your command?
//        attack with grenade
//        You attacked the dragon for 50 damage!
//        It's health is at 10
//        Your health is at 75
//        What is your command?
//        attack with iron sword
//        You attacked the dragon for 15 damage!
//        You have defeated the dragon!
//        Your health is at 55
//        What is your command?
//        eat food
//        Your health is at 75
//        What is your command?
//        eat food
//        There's nothing to eat.
//        What is your command?
//        go north
//        You cannot go here...
//        What is your command?
//        use blue gem
//        The pressure plate clicks in satisfaction as a blinding lights cracks through an opening in the wall at the north.
//        You are thin enough to go through.
//        You used the blue gem.
//        What is your command?
//        go north
//        You've finally reached the top of the tower.
//        You take a look at the world around you and see the sun setting, hear birds chirping, and can smell the warm and earthy air.
//        You've successfully beaten THE DARKEST TOWER.
//
//        Process finished with exit code 0

//        CLASS: CPSC 223J-01
//        GROUP MEMBERS: Jason Lee, Christopher Briseno, Dereck Jimenez, Aren Laure Lizardo

