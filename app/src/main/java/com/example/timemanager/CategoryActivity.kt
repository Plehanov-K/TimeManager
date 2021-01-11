package com.example.timemanager

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.shared.utils.parseTimeFromMinutes
import com.example.timemanager.data.MDate
import com.example.timemanager.ui.home.HomeViewModel
import com.example.timemanager.utils.showAlert
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.net.Proxy
import java.text.SimpleDateFormat


class CategoryActivity : AppCompatActivity() {

    private lateinit var bottomSheet: BottomSheetBehavior<ConstraintLayout>
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        val toolBarDateText = findViewById<TextView>(R.id.toolBarDateText)
        MDate.date.observe(this, Observer {
            toolBarDateText.text = it.toString()
        })

        navView.setupWithNavController(navController)

        val eventDescription = findViewById<EditText>(R.id.event_edit_text_descriprion)

        val bs = findViewById<View>(R.id.bottom_sheet) as ConstraintLayout
        bottomSheet = BottomSheetBehavior.from(bs)

        val timePicker = findViewById<TimePicker>(R.id.time_picker)
        timePicker.setIs24HourView(true)

        val layoutTitle = findViewById<LinearLayout>(R.id.layout_title_add_sheet)
        val titleText = findViewById<TextView>(R.id.event_name_add_sheet)
        homeViewModel.tempEvent.observe(this, Observer {
            eventDescription.text.clear()
            titleText.text = it.name
            layoutTitle.setBackgroundColor(it.color)
            timePicker.currentHour = 0
            timePicker.currentMinute = 0
            bottomSheet.state = BottomSheetBehavior.STATE_EXPANDED
        })


        val buttonEditTitleText = findViewById<ImageButton>(R.id.button_edit_title_add_sheet)
        buttonEditTitleText.setOnClickListener {
            val editText = EditText(this)
            editText.setTextColor(Color.WHITE)
            editText.textSize = 20.0F
            editText.gravity = Gravity.CENTER_HORIZONTAL
            editText.setText(homeViewModel.tempEvent.value?.name)
            MaterialAlertDialogBuilder(this, R.style.ColorPickerDialogTheme)
                .setTitle(getString(R.string.enter_new_name))
                .setNeutralButton(resources.getString(R.string.buttonCancel)) { d, _ ->
                    d.dismiss()
                }
                .setPositiveButton(resources.getString(R.string.buttonOk)) { dialog, _ ->
                    homeViewModel.tempEvent.value?.eventId?.let { it1 ->
                        homeViewModel.updateEventParamNameDb(
                            editText.text.toString().trim(),
                            it1
                        )
                    }
                    bottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
                    dialog.cancel()
                }
                .setView(editText)
                .show()
        }

        val buttonEditColor = findViewById<ImageButton>(R.id.button_edit_color_add_sheet)
        buttonEditColor.setOnClickListener {
            ColorPickerDialogBuilder
                .with(this, R.style.ColorPickerDialogTheme)
                .setTitle(getString(R.string.choose_color))
                .lightnessSliderOnly()
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(10)
                .setPositiveButton(getString(R.string.buttonOk)) { dialog, selectedColor, allColors ->
                    homeViewModel.tempEvent.value?.eventId?.let { it1 ->
                        homeViewModel.updateEventParamColorDb(
                            "#${Integer.toHexString(selectedColor)}",
                            it1
                        )
                    }
                    bottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
                }
                .setNegativeButton(getString(R.string.buttonCancel)) { dialog, which -> }
                .build()
                .show()
        }

        val btnAddEventToday = findViewById<Button>(R.id.buttonAddEvent)
        btnAddEventToday.setOnClickListener {
            val eventTimeInMinutes = timePicker.currentHour.times(60).plus(timePicker.currentMinute)
            homeViewModel.dayTimeInMinutes.value?.let { time ->
                if (time - eventTimeInMinutes >= 0
                ) {
                    homeViewModel.saveEventTodayToDb(
                        eventTimeInMinutes,
                        eventDescription.text.toString()
                    )
                    bottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
                } else Toast.makeText(
                    this,
                    getString(R.string.alert_exceeding_time)+
                            time.parseTimeFromMinutes(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun onClickButton(view: View) {
        homeViewModel.openBottomSheet(view.id)
    }

    fun timeBack(view: View) {
        MDate.setDate(-86_400_000)
    }

    fun timeForward(view: View) {
        MDate.setDate(86_400_000)
    }
}