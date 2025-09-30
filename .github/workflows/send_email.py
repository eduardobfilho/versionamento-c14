import smtplib
import sys
from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart
import os

if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("Uso: python send_email.py <arquivo_mensagem>")
        sys.exit(1)

    mensagem_arquivo = sys.argv[1]

    with open(mensagem_arquivo, "r", encoding="utf-8") as f:
        conteudo = f.read()

    # Variáveis de ambiente vindas do GitHub Secrets
    remetente = os.getenv("EMAIL_USER")
    senha = os.getenv("EMAIL_PASS")
    destinatario = os.getenv("DEST_EMAIL")

    if not remetente or not senha or not destinatario:
        print("Erro: Variáveis de ambiente EMAIL_USER, EMAIL_PASS e DEST_EMAIL são obrigatórias.")
        sys.exit(1)

    # Criar mensagem
    msg = MIMEMultipart()
    msg["From"] = remetente
    msg["To"] = destinatario
    msg["Subject"] = "Notificação CI/CD - Pipeline GitHub Actions"

    msg.attach(MIMEText(conteudo, "plain"))

    try:
        with smtplib.SMTP("smtp.gmail.com", 587) as server:
            server.starttls()
            server.login(remetente, senha)
            server.sendmail(remetente, destinatario, msg.as_string())
            print(f"E-mail enviado com sucesso para {destinatario}")
    except Exception as e:
        print(f"Erro ao enviar e-mail: {e}")
        sys.exit(1)
