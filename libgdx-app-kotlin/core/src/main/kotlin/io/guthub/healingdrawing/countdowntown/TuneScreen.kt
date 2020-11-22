package io.guthub.healingdrawing.countdowntown

import cfg.GameKeeper
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.ExtendViewport


class TuneScreen(private val game:GameKeeper) : ScreenAdapter() {
    private val style = "45"
    private val labelStyle = LabelStyle(game.uiskin.get("text${style}sun",LabelStyle::class.java))
    private val buttonStyle = TextButtonStyle(game.uiskin.get("fon${style}sun",TextButtonStyle::class.java))
    private val scrollStyle = ScrollPaneStyle(game.uiskin.get("text${style}sun",ScrollPaneStyle::class.java))
    private val stage: Stage = Stage()
    private val table: Table = Table()
    private val tableHead = Table()
    private val infoLabel = Label("", labelStyle)
    private val table_scroll: Table = Table()
    private val scroll = ScrollPane(table_scroll, scrollStyle)
    private val table_control: Table = Table()
    private val timers = game.timers
    private var deleted = emptyArray<Int>()
    
    private val repeats = arrayOf(0, 1, 3, 5, 7, 10, -1)
    private val rGroup = ButtonGroup<TextButton>()
    private val tGroup = ButtonGroup<TextButton>()
    
    private fun infoTextCreator(tune:Int, repeats:Int):String{
        val infoText = when(repeats){
            -1 -> "will looped"
            0 -> "will mutted"
            else -> "will play ${repeats} times"
        }
        return "tune ${tune} $infoText"
    }
    
    private fun tableHeadCreator(){
        tableHead.defaults().space(20f)
        tableHead.align(Align.top)
        
        tableHead.width = game.worldWidth
        
        infoLabel.setText(infoTextCreator(game.timer.tune(), game.timer.repeats()))
        tableHead.add(infoLabel).fillX().expandX()
        
        tableHead.row()
        val rt = Table()
        
        
        for (i in 0 until repeats.size){
            val b = TextButton(when(repeats[i]){
                0 -> "mute"
                -1 -> "loop"
                else -> repeats[i].toString()
            }, buttonStyle)
            b.addListener(object : ClickListener() {
                override fun clicked(event: InputEvent, x: Float, y: Float) {
                    infoLabel.setText(infoTextCreator(tGroup.checkedIndex+1, repeats[i]))
                }
            })
            rt.add(b)
            rGroup.add(b)
            if (game.timer.repeats() == repeats[i]) b.isChecked = true
        }
        tableHead.add(rt)
    }
    
    private fun table_scroll_creator(){
//        table_scroll.defaults().space(20f)
        table_scroll.align(Align.top)
        
        /*patterns code*/
        for (i in 0 until timers.size){
            if (i>0) table_scroll.row()
            val timer = timers[i]
            val b = TextButton(timer, buttonStyle)
            b.addListener(object : ClickListener() {
                override fun clicked(event: InputEvent, x: Float, y: Float) {
                    b.isVisible = false
                    deleted += i
                }
            })
            table_scroll.add(b).width(360f)
        }
    }
    
    private fun deleteTimers(){
        for (i in deleted) timers[i] = ""
        var nt = emptyArray<String>()
        for (timer in timers) if (timer.isNotEmpty()) nt += timer
        game.timers = nt.copyOf()
        game.mc.saveTimers()
    }
    fun table_control_creator(){
        table_control.width = game.worldWidth
        table_control.height = 160f
        val bClose = TextButton("CLOSE", buttonStyle)
        bClose.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                deleteTimers()
                game.screen = TimerScreen(game)
            }
        })
        table_control.defaults().space(40f)
        table_control.add(bClose)
    }
    
    override fun show() {
        super.show()
        Gdx.input.inputProcessor = stage
        stage.viewport = ExtendViewport(game.minWidth, game.minHeight)
        resize(Gdx.graphics.width,Gdx.graphics.height)
//        stage.isDebugAll = true
        table.defaults().space(20f)
        
        tableHeadCreator()
        table.add(tableHead).fillX().expandX().align(Align.top)
        table.row()
        
        table_scroll_creator()
        
        scroll.setSize(game.worldWidth,game.worldHeight-440f)
        scroll.setScrollingDisabled(true,false)
        scroll.setCancelTouchFocus(false)
        scroll.setScrollbarsOnTop(true)
        table.add(scroll).align(Align.top).expand().fill()
        table.row()
        
        table_control_creator()
        table.add(table_control).height(160f)
        
        stage.addActor(table)
        table.setFillParent(true)
        
    }
    
    override fun render(delta: Float) {
        super.render(delta)
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        stage.act(delta)
        stage.draw()
    }
    
    private fun refreshSizes(w:Int,h:Int){
        game.refreshSizes(stage.viewport)
        tableHead.setSize(game.worldWidth,320f)
        scroll.setSize(game.worldWidth,game.worldHeight-480f)
        table_control.setSize(game.worldWidth, 160f)
    }
    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        stage.viewport.update(width, height, true)
        refreshSizes(stage.viewport.worldWidth.toInt(), stage.viewport.worldHeight.toInt())
    }
}