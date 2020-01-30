package dev.foodie.notes.background;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import dev.foodie.notes.data.NoteDao;
import dev.foodie.notes.data.NoteDatabase;
import dev.foodie.notes.models.Note;
import kotlinx.coroutines.flow.Flow;

public class AsyncTasks {

    public static class GetNotesByDateModified extends AsyncTask<String, Integer, Flow<List<Note>>> {

        private NoteDao dao;

        public GetNotesByDateModified(NoteDatabase db) {
            this.dao = db.getNoteDao();
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Flow<List<Note>> aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected Flow<List<Note>> doInBackground(String... strings) {
            Flow<List<Note>> flow;
            flow = this.dao.getNotesByDateModified();
            return flow;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }
    public static class GetNotesByTitle extends AsyncTask<String, Integer, Flow<List<Note>>> {

        private NoteDao dao;

        public GetNotesByTitle(NoteDatabase db) {
            this.dao = db.getNoteDao();
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Flow<List<Note>> aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected Flow<List<Note>> doInBackground(String... strings) {
            Flow<List<Note>> flow;
            flow = this.dao.getNotesByTitle();
            return flow;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }

}
