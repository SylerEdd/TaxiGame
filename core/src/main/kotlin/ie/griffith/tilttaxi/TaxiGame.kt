package ie.griffith.tilttaxi

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.graphics.g3d.ModelBatch


class TaxiGame : ApplicationAdapter() {

    private lateinit var batch: ModelBatch
    private lateinit var camera: PerspectiveCamera


    override fun create(){

        batch = ModelBatch()

        camera = PerspectiveCamera(
            55f,
            Gdx.graphics.width.toFloat(),
            Gdx.graphics.height.toFloat()
        )
        camera.position.set(0f, 4f, 8f)
        camera.lookAt(0f, 0.9f, -12f)
        camera.near = 0.1f
        camera.far = 125f
        camera.update()


    }

    override fun dispose(){

    }

    override fun pause(){

    }

    override fun render(){

    }

    override fun resize(width: Int, height: Int) {
        //matching the camera to the screen
        camera.viewportWidth = width.toFloat()
        camera.viewportHeight = height.toFloat()
        camera.update()

    }

    override fun resume(){

    }
}
