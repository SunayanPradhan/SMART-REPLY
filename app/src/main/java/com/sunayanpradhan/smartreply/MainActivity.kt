package com.sunayanpradhan.smartreply

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Telephony.Sms.Conversations
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.mlkit.nl.smartreply.SmartReply
import com.google.mlkit.nl.smartreply.SmartReplyGenerator
import com.google.mlkit.nl.smartreply.SmartReplySuggestionResult
import com.google.mlkit.nl.smartreply.TextMessage
import com.sunayanpradhan.smartreply.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var conversations: ArrayList<TextMessage>

    lateinit var smartReplyGenerator: SmartReplyGenerator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding= DataBindingUtil.setContentView(this,R.layout.activity_main)

        conversations= ArrayList()

        smartReplyGenerator=SmartReply.getClient()


        binding.messageSend.setOnClickListener {

            val message=binding.messageText.text.toString().trim()

            conversations.add(TextMessage.createForRemoteUser(message,System.currentTimeMillis(),"1234"))

            smartReplyGenerator.suggestReplies(conversations).addOnSuccessListener {

                when (it.status) {
                    SmartReplySuggestionResult.STATUS_NOT_SUPPORTED_LANGUAGE -> {

                        binding.suggestionText.text="NOT SUPPORTED LANGUAGE"

                    }
                    SmartReplySuggestionResult.STATUS_SUCCESS -> {

                        var suggestion=""

                        for (i in it.suggestions){

                            suggestion=suggestion+i.text+"\n"

                        }

                        binding.suggestionText.text=suggestion

                    }
                    SmartReplySuggestionResult.STATUS_NO_REPLY -> {

                        binding.suggestionText.text="NO REPLY"

                    }
                }

            }


        }

    }




}