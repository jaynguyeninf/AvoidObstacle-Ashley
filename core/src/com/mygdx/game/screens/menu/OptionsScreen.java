package com.mygdx.game.screens.menu;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Logger;
import com.mygdx.game.AvoidObstacleGame;
import com.mygdx.game.assets.AssetDescriptors;
import com.mygdx.game.assets.AssetPaths;
import com.mygdx.game.common.GameManager;
import com.mygdx.game.configurations.DifficultyLevel;




public class OptionsScreen extends MenuScreenBase {

    private static final Logger log = new Logger(OptionsScreen.class.getName(), Logger.DEBUG);
    private ButtonGroup<CheckBox> checkBoxButtonGroup;
    private CheckBox easyCheckBox, mediumCheckBox, hardCheckBox;


    public OptionsScreen(AvoidObstacleGame game) {
        super(game);
    }

    @Override
    protected Actor createUI() {
        Table table = new Table();
        table.defaults().pad(15);

        TextureAtlas gamePlayAtlas = assetManager.get(AssetDescriptors.GAMEPLAY_ATLAS);
        Skin uiSkin = assetManager.get(AssetDescriptors.UI_SKIN);

        TextureRegion backgroundRegion = gamePlayAtlas.findRegion(AssetPaths.BACKGROUND_REGION);
        table.setBackground(new TextureRegionDrawable(backgroundRegion));

        Label label = new Label("DIFFICULTY", uiSkin);

        easyCheckBox = createCheckBox(DifficultyLevel.EASY.name(), uiSkin); //name() belongs to enum
        mediumCheckBox = createCheckBox(DifficultyLevel.MEDIUM.name(), uiSkin);
        hardCheckBox = createCheckBox(DifficultyLevel.HARD.name(), uiSkin);

        checkBoxButtonGroup = new ButtonGroup<CheckBox>(easyCheckBox, mediumCheckBox, hardCheckBox);
        DifficultyLevel difficultyLevel = GameManager.INSTANCE.getDifficultyLevel();

        checkBoxButtonGroup.setChecked(difficultyLevel.name()); //set button with specified text to checked

        TextButton backButton = new TextButton("BACK", uiSkin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                back();
            }
        });

        ChangeListener changeListener = new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                difficultyChanged();
            }
        };

        easyCheckBox.addListener(changeListener);
        mediumCheckBox.addListener(changeListener);
        hardCheckBox.addListener(changeListener);

        Table contentTable = new Table(uiSkin);
        contentTable.defaults().pad(10);
        contentTable.setBackground(AssetPaths.PANEL);

        contentTable.add(label).row();
        contentTable.add(easyCheckBox).row();
        contentTable.add(mediumCheckBox).row();
        contentTable.add(hardCheckBox).row();
        contentTable.add(backButton);

        table.add(contentTable);
        table.center();
        table.setFillParent(true);
        table.pack();

        return table;
    }


    private void back() {
        log.debug("back()");
        game.setScreen(new MenuScreen(game));
    }

    private void checkedAnimation(CheckBox checkedBox){
            checkedBox.setTransform(true);
            checkedBox.setOrigin(checkedBox.getPrefWidth() / 2, checkedBox.getPrefHeight() / 2);
            checkedBox.addAction(Actions.repeat(1, Actions.sequence(
                            Actions.scaleTo(1.1f, 1.05f, 1f),
                            Actions.scaleTo(1, 1, 1f)
                    )
            ));

    }

    //Change gameplay's difficulty
    private void difficultyChanged() {
        log.debug("difficultyChanged()");
        CheckBox checked = checkBoxButtonGroup.getChecked(); //get checked button from ButtonGroup

        if (checked == easyCheckBox) {
            GameManager.INSTANCE.updateDifficulty(DifficultyLevel.EASY);
            checkedAnimation(easyCheckBox);
        } else if (checked == mediumCheckBox) {
            GameManager.INSTANCE.updateDifficulty(DifficultyLevel.MEDIUM);
            checkedAnimation(mediumCheckBox);
        } else if (checked == hardCheckBox){
            GameManager.INSTANCE.updateDifficulty(DifficultyLevel.HARD);
            checkedAnimation(hardCheckBox);
        }
    }

    private static CheckBox createCheckBox(String text, Skin skin){
        CheckBox createCheckBox = new CheckBox(text,skin);
        createCheckBox.left().pad(8);
        createCheckBox.getLabelCell().pad(8);
        return createCheckBox;
    }

}
