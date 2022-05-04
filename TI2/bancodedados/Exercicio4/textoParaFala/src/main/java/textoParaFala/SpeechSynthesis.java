package textoParaFala;

import com.microsoft.cognitiveservices.speech.*;
import com.microsoft.cognitiveservices.speech.audio.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class SpeechSynthesis {

    public static void speak(String text) throws InterruptedException, ExecutionException {
    	String YourSubscriptionKey = "16df2bfdef7e493b929927578144f122";
    	String YourServiceRegion = "brazilsouth";
    	SpeechConfig speechConfig = SpeechConfig.fromSubscription(YourSubscriptionKey, YourServiceRegion);

        speechConfig.setSpeechSynthesisVoiceName("pt-BR-FranciscaNeural"); 

        SpeechSynthesizer speechSynthesizer = new SpeechSynthesizer(speechConfig);
        
        if (text.isEmpty())
        {
            return;
        }
        
        SpeechSynthesisResult speechRecognitionResult = speechSynthesizer.SpeakTextAsync(text).get();

        if (speechRecognitionResult.getReason() == ResultReason.Canceled) {
            SpeechSynthesisCancellationDetails cancellation = SpeechSynthesisCancellationDetails.fromResult(speechRecognitionResult);
            System.out.println("CANCELED: Reason=" + cancellation.getReason());

            if (cancellation.getReason() == CancellationReason.Error) {
                System.out.println("CANCELED: ErrorCode=" + cancellation.getErrorCode());
                System.out.println("CANCELED: ErrorDetails=" + cancellation.getErrorDetails());
                System.out.println("CANCELED: Did you set the speech resource key and region values?");
            }
        }
    }

    public static String listen() throws InterruptedException, ExecutionException {
        //To recognize speech from an audio file, use `fromWavFileInput` instead of `fromDefaultMicrophoneInput`:
        //AudioConfig audioConfig = AudioConfig.fromWavFileInput("YourAudioFile.wav");
    	String YourSubscriptionKey = "16df2bfdef7e493b929927578144f122";
    	String YourServiceRegion = "brazilsouth";
    	SpeechConfig speechConfig = SpeechConfig.fromSubscription(YourSubscriptionKey, YourServiceRegion);
        speechConfig.setSpeechRecognitionLanguage("pt-BR");
        
        String textListened = new String();
        
    	AudioConfig audioConfig = AudioConfig.fromDefaultMicrophoneInput();
        SpeechRecognizer speechRecognizer = new SpeechRecognizer(speechConfig, audioConfig);

        
        Future<SpeechRecognitionResult> task = speechRecognizer.recognizeOnceAsync();
        SpeechRecognitionResult speechRecognitionResult = task.get();

        if (speechRecognitionResult.getReason() == ResultReason.RecognizedSpeech) {
        	textListened = speechRecognitionResult.getText();
        }
        else if (speechRecognitionResult.getReason() == ResultReason.NoMatch) {
            System.out.println("NOMATCH: Speech could not be recognized.");
        }
        else if (speechRecognitionResult.getReason() == ResultReason.Canceled) {
            CancellationDetails cancellation = CancellationDetails.fromResult(speechRecognitionResult);
            System.out.println("CANCELED: Reason=" + cancellation.getReason());

            if (cancellation.getReason() == CancellationReason.Error) {
                System.out.println("CANCELED: ErrorCode=" + cancellation.getErrorCode());
                System.out.println("CANCELED: ErrorDetails=" + cancellation.getErrorDetails());
                System.out.println("CANCELED: Did you set the speech resource key and region values?");
            }
        }

        return textListened;
    }
    
    public static void main(String[] args) throws InterruptedException, ExecutionException {
    	String nome = new String();
    	String idade = new String();
    	String gostos = new String();
    	
    	
    	speak("Me diga seu nome");
    	nome = listen();
    	speak("Qual a sua idade?");
    	idade = listen();
    	speak("Do que você gosta?");
    	gostos = listen();
    	speak("Seu nome é " + nome + " e você tem " + idade + ". E seus gostos são: " + gostos);
    	speak("Também irei imprimir o que me falou na tela");
    	System.out.println("Nome: " + nome);
    	System.out.println("Idade: " + idade);
    	System.out.println("Gostos: " + gostos);
        //System.out.println("Enter some text that you want to speak >");
        
    }
}