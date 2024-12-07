package io.mygame.ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.mygame.datahandler.JSONService;
import io.mygame.datahandler.Serializable;
import io.mygame.screens.GameScreen;
import io.mygame.screens.ScreenState;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DialogueUI extends UI implements Serializable {
    private String[] janitorDialogue;
    private String[] guardDialogue;
    private String[] teacherDialogue;
    private String[] collegeMaleDialogue;
    private String[] collegeFemaleDialogue;
    private String[] petDialogue;

    private final transient Random random;
    private transient List<String> unusedJanitorDialogues;
    private transient List<String> unusedGuardDialogues;
    private transient List<String> unusedTeacherDialogues;
    private transient List<String> unusedCollegeMaleDialogues;
    private transient List<String> unusedCollegeFemaleDialogues;
    private transient List<String> unusedPetDialogues;

    private transient String fullDialogue;
    private transient float typewriterTimer = 0;
    private transient int visibleCharacters = 0;
    private transient Table root;
    private transient boolean isDialogueOn = false;
    private transient boolean isDialogueTextFinished = false;
    private transient Label dialogueLabel;
    private transient Label nameLabel;

    public DialogueUI(ScreenViewport screenViewport, ScreenState screenState, Game game) {
        super(screenViewport, screenState, game);

        random = new Random();
        unusedJanitorDialogues = new ArrayList<>();
        unusedGuardDialogues = new ArrayList<>();
        unusedTeacherDialogues = new ArrayList<>();
        unusedCollegeMaleDialogues = new ArrayList<>();
        unusedCollegeFemaleDialogues = new ArrayList<>();
        unusedPetDialogues = new ArrayList<>();

        JSONService jsonService = new JSONService();
        DialogueUI dialogues;

        String dialogueJsonPath = "dialogues/dialogue.json";
        try (FileReader fileReader = new FileReader(dialogueJsonPath)) {
            dialogues = jsonService.deserialize(fileReader, DialogueUI.class);
            this.janitorDialogue = dialogues.janitorDialogue;
            this.guardDialogue = dialogues.guardDialogue;
            this.teacherDialogue = dialogues.teacherDialogue;
            this.collegeMaleDialogue = dialogues.collegeMaleDialogue;
            this.collegeFemaleDialogue = dialogues.collegeFemaleDialogue;
            this.petDialogue = dialogues.petDialogue;
        } catch (IOException e) {
            System.err.println("DialogueUIManager: Failed to load dialogues json");
        }
    }

    public void dialogueBox(String npcType) {
        if(isDialogueOn) {
            if(Gdx.input.isTouched() || Gdx.input.isKeyPressed(Input.Keys.E))  {
                if(!isDialogueTextFinished) {
                    dialogueLabel.setText(fullDialogue);
                    isDialogueTextFinished = true;
                } else {
                    removeDialogue();
                    endDialogue();
                }
                return;
            }
        }

        startDialogue();

        root = new Table();
        root.align(Align.bottom);
        root.padBottom(30f);
        root.setFillParent(true);

        Stack stack = new Stack();

        Image image = new Image(skin, "dialogueBox.9");
        stack.addActor(image);

        Container container = new Container();
        container.align(Align.left);
        container.padLeft(125.0f);

        Container container1 = new Container();
        container1.align(Align.left);
        container1.fillX();
        container1.width(750.0f);
        container1.height(75.0f);
        container1.padTop(20.0f);

        dialogueLabel = new Label("", skin, "dialogueLabel");
        dialogueLabel.setAlignment(Align.topLeft);
        dialogueLabel.setWrap(true);
        container1.setActor(dialogueLabel);
        container.setActor(container1);
        stack.addActor(container);

        container = new Container();
        container.align(Align.topLeft);
        container.padLeft(100.0f);
        container.padTop(25.0f);

        nameLabel = new Label("", skin, "dialogueLabel");
        container.setActor(nameLabel);
        stack.addActor(container);

        root.add(stack);
        stage.addActor(root);

        setDialogue(npcType);
    }

    public void setDialogue(String npcType) {
        switch (npcType) {
            case "Janitor":
                gameManager.setNpcInteracted("Janitor");
                fullDialogue = getJanitorRandomDialogue();
                nameLabel.setText("Janitor");
                break;
            case "Guard":
                gameManager.setNpcInteracted("Guard");
                fullDialogue = getGuardRandomDialogue();
                nameLabel.setText("Guard");
                break;
            case "Teacher":
                gameManager.setNpcInteracted("Teacher");
                fullDialogue = getTeacherRandomDialogue();
                nameLabel.setText("Professor");
                break;
            case "MaleStudent":
                gameManager.setNpcInteracted("MaleStudent");
                fullDialogue = getCollegeMaleRandomDialogue();
                nameLabel.setText("Male CIT-U Student");
                break;
            case "FemaleStudent":
                gameManager.setNpcInteracted("FemaleStudent");
                fullDialogue = getCollegeFemaleRandomDialogue();
                nameLabel.setText("Female CIT-U Student");
                break;
            case "Pet":
                gameManager.setNpcInteracted("Pet");
                fullDialogue = getPetRandomDialogue();
                nameLabel.setText("Cat");
                break;
            default:
                throw new IllegalArgumentException("No such dialogue type: " + npcType);
        }
        visibleCharacters = 0;
        typewriterTimer = 0;
    }

    public void update(float delta) {
        if (fullDialogue != null && visibleCharacters < fullDialogue.length() && !isDialogueTextFinished) {
            typewriterTimer += delta;

            if (typewriterTimer > 0.05f) {
                visibleCharacters++;
                dialogueLabel.setText(
                    fullDialogue.substring(0, visibleCharacters)
                );
                typewriterTimer = 0;
            }
        } else {
            isDialogueTextFinished = true;
        }
    }

    public void removeDialogue() {
        root.remove();
    }

    private String getJanitorRandomDialogue() {
        if (unusedJanitorDialogues.isEmpty()) {
            unusedJanitorDialogues = new ArrayList<>(List.of(janitorDialogue));
        }

        int index = random.nextInt(unusedJanitorDialogues.size());
        String selectedDialogue = unusedJanitorDialogues.get(index);

        unusedJanitorDialogues.remove(index);

        return selectedDialogue;
    }

    private String getGuardRandomDialogue() {
        if (unusedGuardDialogues.isEmpty()) {
            unusedGuardDialogues = new ArrayList<>(List.of(guardDialogue));
        }

        int index = random.nextInt(unusedGuardDialogues.size());
        String selectedDialogue = unusedGuardDialogues.get(index);

        unusedGuardDialogues.remove(index);

        return selectedDialogue;
    }

    private String getTeacherRandomDialogue() {
        if (unusedTeacherDialogues.isEmpty()) {
            unusedTeacherDialogues = new ArrayList<>(List.of(teacherDialogue));
        }

        int index = random.nextInt(unusedTeacherDialogues.size());
        String selectedDialogue = unusedTeacherDialogues.get(index);

        unusedTeacherDialogues.remove(index);

        return selectedDialogue;
    }

    private String getCollegeMaleRandomDialogue() {
        if (unusedCollegeMaleDialogues.isEmpty()) {
            unusedCollegeMaleDialogues = new ArrayList<>(List.of(collegeMaleDialogue));
        }

        int index = random.nextInt(unusedCollegeMaleDialogues.size());
        String selectedDialogue = unusedCollegeMaleDialogues.get(index);

        unusedCollegeMaleDialogues.remove(index);

        return selectedDialogue;
    }

    private String getCollegeFemaleRandomDialogue() {
        if (unusedCollegeFemaleDialogues.isEmpty()) {
            unusedCollegeFemaleDialogues = new ArrayList<>(List.of(collegeFemaleDialogue));
        }

        int index = random.nextInt(unusedCollegeFemaleDialogues.size());
        String selectedDialogue = unusedCollegeFemaleDialogues.get(index);

        unusedCollegeFemaleDialogues.remove(index);

        return selectedDialogue;
    }

    private String getPetRandomDialogue() {
        if (unusedPetDialogues.isEmpty()) {
            unusedPetDialogues = new ArrayList<>(List.of(petDialogue));
        }

        int index = random.nextInt(unusedPetDialogues.size());
        String selectedDialogue = unusedPetDialogues.get(index);

        unusedPetDialogues.remove(index);

        return selectedDialogue;
    }

    private void startDialogue() {
        ((GameScreen) screenState).setPaused(true);
        isDialogueOn = true;
        isDialogueTextFinished = false;
    }

    private void endDialogue() {
        ((GameScreen) screenState).setPaused(false);
        isDialogueOn = false;
    }

    @Override
    public void render() {
        update(Gdx.graphics.getDeltaTime());
        super.render();
    }
}
