package munoz.guerrero.erick.lista

import android.annotation.SuppressLint
import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.*
import java.util.*


/**
 * A placeholder fragment containing a simple view.
 */
class MainActivityFragment : Fragment(),AdapterView.OnItemClickListener ,SeekBar.OnSeekBarChangeListener, TextToSpeech.OnInitListener{
    var tts : TextToSpeech? = null
    var progreso: Int? = null
    var adaptador: ArrayAdapter<String>? = null
    var listView: ListView? = null

    var elementos = arrayOfNulls<String>(11)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val vistaRaiz = inflater.inflate(R.layout.fragment_main, container, false)

        val seek = vistaRaiz.findViewById<SeekBar>(R.id.seekBar) as SeekBar
        tts = TextToSpeech(context,this)
        Log.i("Languages",Locale.getAvailableLocales().toString())

        progreso = seek.progress //3

        seek.setOnSeekBarChangeListener(this)

        listView = vistaRaiz.findViewById<ListView>(R.id.listView) as ListView
        listView!!.setOnItemClickListener(this)

        calculaTablas(progreso!!)

        return vistaRaiz
    }



    fun calculaTablas(progreso : Int){
        for(i in 0..10){
            val texto = "$progreso x $i = ${progreso*i}"
            elementos.set(i,texto)
        }

        adaptador = ArrayAdapter<String>(context,
                android.R.layout.simple_list_item_1,
                elementos)
        listView!!.adapter = adaptador

    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS){
            val result = tts!!.setLanguage(Locale("es_MX"))
            if (result != TextToSpeech.LANG_NOT_SUPPORTED || result != TextToSpeech.LANG_MISSING_DATA){

            }else{
                Toast.makeText(context,"no soportado", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onDestroy() {
        if (tts != null){
            tts!!.stop()
            tts!!.shutdown()

        }
        super.onDestroy()
    }


    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        var texto = elementos.get(position)!!.replace("*"," por ")
        Toast.makeText(context,"presionado",Toast.LENGTH_SHORT).show()
        tts!!.speak(texto,TextToSpeech.QUEUE_FLUSH,null,null)

    }



    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
        calculaTablas(p1)
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {
    }

    override fun onStopTrackingTouch(p0: SeekBar?) {
    }

}
