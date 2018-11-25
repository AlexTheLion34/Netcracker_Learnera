<template>
  <v-container v-if="template">
    <v-form v-model="formValid">
      <v-layout row wrap>
        <v-flex d-flex justify-center align-center xs12>
          <h3 class="headline">{{ readOnly ? 'View' : 'Edit' }} template</h3>
        </v-flex>
        <v-flex xs12>
          <v-layout align-start justify-start row>
            <v-avatar size="100" color="teal">
              <img v-if="template.info && template.info.avatar" :src="template.info.avatar">
              <span v-else-if="template.name" class="ava">{{ template.name[0] }}</span>
            </v-avatar>
            <v-flex>
              <v-layout column>
                <v-layout justify-space-between row>
                  <v-flex xs2 style="margin-left: 5px" fill-height>
                    <v-text-field v-if="!readOnly" v-model="template.name" 
                                  :rules="nameRules" label="Template name"/>
                    <div v-else class="head">{{ template.name ? template.name : '' }}</div>
                  </v-flex>
                  <v-spacer/>
                  <v-flex fill-height xs2>
                    <div>
                      <v-btn v-if="teacher" :to="`/user/${teacher.id}`" color="primary">
                        Teacher: {{ teacherName }}
                      </v-btn>
                    </div>
                  </v-flex>
                </v-layout>
                <v-flex>
                  <v-layout justify-space-between row>
                    <v-flex fill-height xs2>
                      <v-chip :color="(template.completed ? 'orange' : 'green') + ' darken-3'" small text-color="white" disabled>
                        {{ template.completed ? 'FINAL' : 'EDITABLE' }}
                      </v-chip>
                    </v-flex>
                    <v-spacer/>
                  </v-layout>
                </v-flex>
              </v-layout>
            </v-flex>
          </v-layout>
        </v-flex>
        <v-flex style="margin: 15px 0 0 0">
          <v-textarea v-model="template.description" :readonly="readOnly" box label="Description" auto-grow/>
        </v-flex>
        <v-flex xs12>
          <v-card>
            <v-card-title><h3 class="headline mb-0">Modules</h3></v-card-title>
            <v-responsive>
              <v-tabs v-model="currentModule" color="cyan" dark slider-color="yellow">
                <template v-for="(module, i) in modules">
                  <v-tab :key="`tab-w-${i}`" ripple>
                    <v-layout row fill-height>
                      <v-flex d-flex align-center="">
                        <v-text-field v-if="!readOnly && !template.completed" v-model="template.modules[i].name"
                                      :rules="nameRules" style="margin: 0 0 0 0"/>
                        <div v-else>
                          {{ module.name ? module.name : 'Module ' + (module.moduleNumber + 1) }}
                        </div>
                      </v-flex>
                      <v-flex v-if="!template.completed && !readOnly" d-flex align-center>
                        <div>
                          <v-btn style="margin-right: -5px; margin-left: 5px;" small flat icon 
                                 @click.stop="removeModule(i)">
                            <v-icon color="red darken-4">remove</v-icon>
                          </v-btn>
                        </div>
                      </v-flex>
                    </v-layout>
                  </v-tab>
                  <v-divider :key="`div-w-${i}`" class="cyan lighten-4" inset vertical/>
                </template>
                
                <v-tooltip v-if="!template.completed && !readOnly" bottom>
                  <v-btn slot="activator" icon ripple @click.stop="addModule">
                    <v-icon color="grey lighten-3">add</v-icon>
                  </v-btn>
                  <span>Add module</span>
                </v-tooltip>

                <v-tab-item v-for="(module, i) in modules" :key="`tab-item-${i}`">
                  <v-card>
                    
                    <v-tabs color="teal lighten-1" dark slider-color="yellow">
                      <template v-for="(lesson, j) in module.lessons">
                        <v-tab :key="`tab-w-${i}-l-${j}`" ripple>
                          <v-layout row fill-height>
                            <v-flex d-flex align-center="">
                              <div>
                                {{ lesson.name ? lesson.name : ('Lesson ' + (lesson.ordering + 1)) }}
                              </div>
                            </v-flex>
                            <v-flex v-if="!template.completed && !readOnly" d-flex align-center>
                              <div>
                                <v-btn style="margin-right: -5px; margin-left: 5px;" small flat icon
                                       @click.stop="removeLesson({moduleIndex: i, lessonIndex: j})">
                                  <v-icon color="red darken-4">remove</v-icon>
                                </v-btn>
                              </div>
                            </v-flex>
                          </v-layout>
                        </v-tab>
                        <v-divider :key="`div-w-${i}-l-${j}`" 
                                   class="teal lighten-4" inset vertical/>
                      </template>

                      <v-tooltip v-if="!template.completed && !readOnly" bottom>
                        <v-btn slot="activator" icon ripple @click.stop="addLesson(i)">
                          <v-icon color="grey lighten-3">add</v-icon>
                        </v-btn>
                        <span>Add lesson</span>
                      </v-tooltip>

                      <v-tab-item v-for="(lesson, j) in module.lessons"
                                  :key="`tab-item-w-${i}-l-${j}`">
                        <v-card flat>
                          <lesson-editor v-model="template.modules[i].lessons[j]" :editable="!template.completed && !readOnly"
                                         @update:valid="$set(moduleValids[i], j, $event)"/>
                        </v-card>
                      </v-tab-item>
                    </v-tabs>
                  </v-card>
                </v-tab-item>
              </v-tabs>
            </v-responsive>
          </v-card>
        </v-flex>
        <v-flex v-if="!readOnly" xs12 style="margin: 15px 0 0 0">
          <v-card>
            <v-card-title><h3 class="headline mb-0">Confirm your action</h3></v-card-title>
            <v-responsive>
              <v-layout column>  
                <v-flex>
                  <error-message v-model="showError" :message="hasError"/>
                </v-flex>
                <v-flex>
                  <v-layout row>
                    <v-flex style="margin: 0 10px 0 10px" xs4>
                      <v-btn :disabled="!!hasError" color="primary" block @click="onTemplatePost">Save template</v-btn>
                    </v-flex>
                    <v-flex style="margin: 0 10px 0 10px" xs4>
                      <v-btn :disabled="!template.id || template.completed || !!hasError" color="secondary" block @click="onFinalize">
                        Finalize
                      </v-btn>
                    </v-flex>
                    <v-flex style="margin: 0 10px 0 10px" xs4>
                      <v-btn color="error" block @click="$router.back()">
                        Cancel
                      </v-btn>
                    </v-flex>
                  </v-layout>
                </v-flex>
              </v-layout>
            </v-responsive>
          </v-card>
        </v-flex>
      </v-layout>
    </v-form>
  </v-container>
</template>

<script>
import {mapState, mapActions, mapGetters} from 'vuex';
import {store} from '../store'
import {router} from '../router'
import LessonEditor from './base/LessonEditor.vue'
import ErrorMessage from './base/ErrorMessage.vue'

export default {
  name: 'TemplateViewer',
  components: {LessonEditor, ErrorMessage},
  props: ['templateInput', 'templateIdStr', 'readOnly'],
  data() {
    return {
      formValid: false,
      moduleValids: [],
      nameRules: [
        v => !!v || 'Name is required',
        v => !!v && v.length <= 30 || 'Name must be less than 30 characters'
      ],
      showError: true,
      currentModule: null,
      template: {
        id: null,
        teacher: null,
        name: '',
        description: '',
        completed: false,
        avatar: null,
        modules: []
      },
    };
  },
  computed: {
    ...mapState('account', {currentUser: 'user'}),
    ...mapState('users', {
      teacher(state) {
        if (this.template.teacher && this.template.teacher.id) {
          const ret = state.items.find(x => x.id === this.template.teacher.id);
          return ret;
        } else {
          return null;
        }
      }
    }),
    userId: function() {
      return this.currentUser.id;
    },
    modules: function() {
      if (!this.template) {
        return undefined;
      }
      return this.template.modules;
    },
    teacherName: function() {
      if (!this.teacher) { return ''; }
      if (this.teacher.info) {
        return this.teacher.info.firstName || this.teacher.info.lastName || this.teacher.email;
      }
      return this.teacher.email;
    },
    hasError: function() {
      if (!this.modules || this.modules.length === 0) {
        return 'There should be at least one module';
      }
      if (!this.formValid) {
        return 'Fix all fields';
      }
      const lessonsPresent = this.modules.reduce((acc, x) => acc && x.lessons && x.lessons.length > 0, true);
      if (!lessonsPresent) {
        return 'There should be at least one lesson in all modules';
      }

      const allModulesValid = this.moduleValids.reduce((acc, x) => acc && x.reduce((acc, y) => acc && y, true), true);
      if (!allModulesValid) {
        return 'One of the modules is invalid';
      }

      return '';
    }
  },
  watch: {
    modules(val) {
      if (!val) {
        return;
      }
      this.moduleValids = val.map(w => {
        if (!w.lessons) {
          return [];
        }

        return w.lessons.map(l => true);
      });
    },
    hasError(val) {
      this.showError = !!val;
    }
  },
  beforeMount() {
    if (this.templateInput) {
      this.template = this.templateInput;
      this.template.teacher = {id: this.currentUser.id};
    } else if (this.templateIdStr) {
      this.getTemplate(parseInt(this.templateIdStr)).then(t => {
        this.template = t;
        this.template.teacher = {id: this.template.teacher};
        this.getUser(this.template.teacher.id);
        this.transformTemplate();
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
      getLessonsByModuleId: 'getByModuleId'
    }),
    ...mapActions('users', {
      getUser: 'get'
    }),
    ...mapActions('alert', {
      alertError: 'error',
      alertSuccess: 'success'
    }),
    transformTemplate() {
      if (this.template.modules) {
        this.template.modules[0].template = {id: this.template.id};
        this.template.modules = this.template.modules.map(w => {
          this.getLessonsByModuleId(w.id).then(ls => {
            if (ls) {
              ls[0].module = {id: w.id}
            }
            w.lessons = ls;
          });
          return w;
        });
      }
    },
    transformForPost() {
      this.template.teacher = {id: this.currentUser.id};
      if (this.template.modules) {
        this.template.modules = this.template.modules.map(w => {
          w.template = null;

          if (w.lessons) {
            w.lessons = w.lessons.map(l => {
              l.module = null;
              l.messages = null;

              if (l.questions) {
                l.questions = l.questions.map(q => {
                  q.assignment = null;
                  q.attempts = null;

                  if (q.variants) {
                    q.variants = q.variants.map(v => {
                      v.question = null;
                      return v;
                    });
                  }

                  return q;
                });
              }
              return l;
            });
          }
          return w;
        });
      }
    },
    onTemplatePost() {
      this.transformForPost();
      if (this.template.id) {

        this.updateTemplate(this.template).then(template => {
          this.$emit('template-updated', template);
          this.alertSuccess('Template updated successfully');
          this.template = template;
          this.transformTemplate();
        }).catch(e => this.alertError(`Template failed to update: ${e.data.message}`));
      } else {
        this.createTemplate(this.template).then(template => {
          this.$emit('template-created', template);
         
          this.alertSuccess('Template created successfully');

          this.template = template;
          this.transformTemplate();
          this.$router.push(`/template/${template.id}/edit`);
        }).catch(e => this.alertError(`Template failed to create: ${e.data.message}`));
      }
    },
    onFinalize() {
      this.transformForPost();
      this.template.completed = true;
      this.updateTemplate(this.template).then(template => {
        this.$emit('template-finalized', template);
        this.alertSuccess('Template finalized successfully');

        this.template = template;
        this.transformTemplate();
      }).catch(e => this.alertError(`Template failed to finalize: ${e.data.message}`));
    },
    addModule() {
      this.template.modules.push({
        template: this.template.id ? {id: this.template.id} : null,
        moduleNumber: this.modules.length,
        name: `Module ${this.modules.length + 1}`,
        lessons: []
      });
    },
    removeModule(index) {
      this.template.modules.splice(index, 1);
      let i = 0;
      this.template.modules.forEach(w => w.moduleNumber = (i++));
    },
    addLesson(moduleIndex) {
      this.modules[moduleIndex].lessons.push({
        ordering: this.modules[moduleIndex].lessons.length,
        lectureText: 'HELLO!',
        type: 'lecture'
      });
    },
    removeLesson({moduleIndex, lessonIndex}) {
      this.modules[moduleIndex].lessons.splice(lessonIndex, 1);
      let i = 0;
      this.modules[moduleIndex].lessons.forEach(l => l.ordering = (i++));
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
