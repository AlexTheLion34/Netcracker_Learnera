<template>
  <v-container v-if="user">
    <v-layout row wrap>
      <v-flex d-flex justify-center align-center xs12>
        <h3 class="headline">Edit template</h3>
      </v-flex>
      <v-flex xs12>
        <v-text-field v-model="template.name" label="Template name"/>
      </v-flex>
      <v-flex xs12>
        <v-layout row>
          <v-flex>
            <v-avatar size="100" color="teal">
              <span>TODO</span>
            </v-avatar>
            <v-btn>Load avatar</v-btn>
          </v-flex>
        </v-layout>
      </v-flex>
      <v-flex style="margin: 15px 0 0 0">
        <v-textarea v-model="template.description" box label="Description" auto-grow/>
      </v-flex>
      <v-flex xs12>
        <v-card>
          <v-card-title><h3 class="headline mb-0">Weeks</h3></v-card-title>
          <v-responsive>
            <v-tabs color="cyan" dark slider-color="yellow">
              <v-tab v-for="week in weeks" :key="week.id" ripple>
                {{ week.name ? week.name : 'Week ' + (week.weekNumber + 1) }}
              </v-tab>
              
              <v-tooltip v-if="!template.completed" bottom>
                <v-btn slot="activator" icon ripple @click.prevent="addWeek">
                  <v-icon color="grey lighten-3">add</v-icon>
                </v-btn>
                <span>Add week</span>
              </v-tooltip>

              <v-tab-item v-for="week in weeks" :key="week.id">
                <v-card>
                  <v-tabs color="teal lighten-1" dark slider-color="yellow">
                    <v-tab v-for="(lesson, index) in week.lessons" :key="week.weekNumber * 100 + index" ripple>
                      {{ lesson.name ? lesson.name : ('Lesson ' + (lesson.ordering + 1)) }}
                    </v-tab>

                    <v-tooltip v-if="!template.completed" bottom>
                      <v-btn slot="activator" icon ripple @click.prevent="addLesson(week)">
                        <v-icon color="grey lighten-3">add</v-icon>
                      </v-btn>
                      <span>Add lesson</span>
                    </v-tooltip>

                    <v-tab-item v-for="(lesson, index) in week.lessons" :key="week.weekNumber * 100 + index">
                      <v-card flat>
                        Lesson info should be here.
                      </v-card>
                    </v-tab-item>
                  </v-tabs>
                </v-card>
              </v-tab-item>
            </v-tabs>
          </v-responsive>
        </v-card>
      </v-flex>
      <v-flex xs12 style="margin: 15px 0 0 0">
        <v-card>
          <v-card-title><h3 class="headline mb-0">Confirm your action</h3></v-card-title>
          <v-responsive>
            <v-layout row>
              <v-flex style="margin: 0 10px 0 10px" xs4>
                <v-btn color="primary" block @click="onTemplatePost">Save template</v-btn>
              </v-flex>
              <v-flex style="margin: 0 10px 0 10px" xs4>
                <v-btn :disabled="!template.id || template.completed" color="secondary" block @click="onFinalize">
                  Finalize
                </v-btn>
              </v-flex>
              <v-flex style="margin: 0 10px 0 10px" xs4>
                <v-btn color="error" block @click="$router.back()">
                  Cancel
                </v-btn>
              </v-flex>
            </v-layout>
          </v-responsive>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import {mapState, mapActions, mapGetters} from 'vuex';
import {store} from '../store'
import {router} from '../router'

export default {
  name: 'TemplateEditor',
  props: ['templateInput', 'templateIdStr'],
  data() {
    return {
      template: {
        id: null,
        teacher: null,
        name: '',
        description: '',
        completed: false,
        avatar: null,
        weeks: []
      },
    };
  },
  computed: {
    ...mapState('account', {currentUser: 'user'}),
    ...mapState('users', {
      user(state) { 
        const ret = state.items.find(x => x.id === this.userId);
        return ret;
      }
    }),
    userId: function() {
      return this.currentUser.id;
    },
    weeks: function() {
      if (!this.template) {
        return undefined;
      }
      return this.template.weeks;
    }
  },
  watch: {
    userId: function(val) {
      this.getUser(val);
    },
  },
  beforeMount() {
    this.getUser(this.userId);
    if (this.templateInput) {
      this.template = this.templateInput;
    } else if (this.templateIdStr) {
      this.getTemplate(parseInt(this.templateIdStr)).then(t => {
        this.template = t;
        this.transformTemplate();
        console.log(JSON.stringify(this.template));
      }).catch(e => console.err);
    }
  },
  methods: {
    ...mapActions('templates', {
      createTemplate: 'create',
      updateTemplate: 'update',
      getTemplate: 'get'
    }),
    ...mapActions('lessons', {
      getLessonsByWeekId: 'getByWeekId'
    }),
    ...mapActions('users', {
      getUser: 'get'
    }),
    transformTemplate() {
      this.template.teacher = {id: this.currentUser.id};
      if (this.template.weeks) {
        this.template.weeks[0].template = {id: this.template.id};
        this.template.weeks = this.template.weeks.map(w => {
          this.getLessonsByWeekId(w.id).then(ls => {
            if (ls) {
              ls[0].week = {id: w.id}
            }
            w.lessons = ls;
          });
          return w;
        });
      }
    },
    onTemplatePost() {
      this.template.teacher = {id: this.currentUser.id};
      console.log(JSON.stringify(this.template));
      if (this.template.id) {
        this.updateTemplate(this.template).then(template => {
          console.log('Template updated!');
          this.$emit('template-updated', template);

          this.template = template;
          this.transformTemplate();
        }).catch(e => console.error);
      } else {
        this.createTemplate(this.template).then(template => {
          console.log('Template created');
          this.$emit('template-created', template);
          // TODO: IMPLEMENT NOTIFICATION
          this.template = template;
          this.transformTemplate();
        }).catch(e => console.error);
      }
    },
    onFinalize() {
      this.template.completed = true;
      this.template.teacher = {id: this.currentUser.id};
      console.log(JSON.stringify(this.template));
      this.updateTemplate(this.template).then(template => {
        console.log('Template finalized!');
        this.$emit('template-finalized', template);

        this.template = template;
        this.transformTemplate();
      })
    },
    addWeek() {
      this.weeks.push({
        template: this.template.id ? {id: this.template.id} : null,
        weekNumber: this.weeks.length,
        name: '',
        lessons: []
      });
    },
    addLesson(week) {
      const idx = this.weeks.findIndex(w => w.weekNumber === week.weekNumber);
      this.weeks[idx].lessons.push({
        ordering: this.weeks[idx].lessons.length,
        lectureText: 'HELLO!',
        type: 'lecture' // TODO
      });
      // TODO: IMPLEMENT
    }
  },
}
</script>

<style>
.ava {
  font-size: 5em;
  color: white;
  text-align: center;
}

.mainInfo {
  padding-left: 0.4em;
}

.head {
  font-size: 1.5em;
  color: black;
}

</style>
