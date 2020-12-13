<template id="add-new-sumbission">
  <div>
    <v-toolbar color="white">
      <v-toolbar-title>Life Cube</v-toolbar-title>
    </v-toolbar>

    <v-container fluid grid-list-md>
      <v-form ref="newSubmissionForm" v-model="valid" :lazy-validation=false>
        <v-flex xs12 sm6 md3>
          <v-textarea v-model="submission.text"
                      label="Submission"
                      :rules="submissionRules"
                      solo
                      hint="Add you goal, idea or dream..."
                      auto-grow>
          </v-textarea>
          <br>
        </v-flex>

        <v-flex xs12 sm3 d-flex>
          <v-select v-model="submission.type" :items="submissionTypes" label="Type"></v-select>
        </v-flex>

        <v-btn class="ma-2" medium color="red" @click="cancel">Cancel</v-btn>
        <v-btn class="ma-2" medium color="primary" :disabled="!valid"
               @click="addNewSubmission">Save
        </v-btn>

      </v-form>
    </v-container>
  </div>

</template>

<script>
import api from './submission-backend-api'
import router from "@/router";

export default {
  data() {
    return {
      valid: false,
      submissionRules: [
        v => (!v || v.length >= 4) || 'Submission text must be at least 4 characters',
        v => (!v || v.length <= 1000) || 'Submission text must be less than 1000 characters',
        v => !!v || 'Submission text is required',
      ],
      submission: {text: "", type: "DREAM"},
      submissionTypes: ["DREAM", "AMBITION", "GOAL"]
    }
  },
  methods: {
    recaptcha() {
      return this.$recaptcha('add_new_submission')
    },

    cancel() {
      router.push("/dashboard")
    },

    addNewSubmission() {
      return this.recaptcha().then((token) => {
        return api.addNewSubmission(this.submission, token).then(() => {
          router.push("/dashboard")
        });
      })
    },
  },
}
</script>
